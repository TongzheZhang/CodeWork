# Copyright 2016 The TensorFlow Authors. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# ==============================================================================
from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import numpy as np
from tensorflow.contrib import distributions
from tensorflow.python.framework import tensor_shape
from tensorflow.python.ops import array_ops
from tensorflow.python.ops import math_ops
from tensorflow.python.platform import test

ds = distributions


class MultinomialTest(test.TestCase):

  def setUp(self):
    self._rng = np.random.RandomState(42)

  def testSimpleShapes(self):
    with self.test_session():
      p = [.1, .3, .6]
      dist = ds.Multinomial(total_count=1., probs=p)
      self.assertEqual(3, dist.event_shape_tensor().eval())
      self.assertAllEqual([], dist.batch_shape_tensor().eval())
      self.assertEqual(tensor_shape.TensorShape([3]), dist.event_shape)
      self.assertEqual(tensor_shape.TensorShape([]), dist.batch_shape)

  def testComplexShapes(self):
    with self.test_session():
      p = 0.5 * np.ones([3, 2, 2], dtype=np.float32)
      n = [[3., 2], [4, 5], [6, 7]]
      dist = ds.Multinomial(total_count=n, probs=p)
      self.assertEqual(2, dist.event_shape_tensor().eval())
      self.assertAllEqual([3, 2], dist.batch_shape_tensor().eval())
      self.assertEqual(tensor_shape.TensorShape([2]), dist.event_shape)
      self.assertEqual(tensor_shape.TensorShape([3, 2]), dist.batch_shape)

  def testN(self):
    p = [[0.1, 0.2, 0.7], [0.2, 0.3, 0.5]]
    n = [[3.], [4]]
    with self.test_session():
      dist = ds.Multinomial(total_count=n, probs=p)
      self.assertEqual((2, 1), dist.total_count.get_shape())
      self.assertAllClose(n, dist.total_count.eval())

  def testP(self):
    p = [[0.1, 0.2, 0.7]]
    with self.test_session():
      dist = ds.Multinomial(total_count=3., probs=p)
      self.assertEqual((1, 3), dist.probs.get_shape())
      self.assertEqual((1, 3), dist.logits.get_shape())
      self.assertAllClose(p, dist.probs.eval())

  def testLogits(self):
    p = np.array([[0.1, 0.2, 0.7]], dtype=np.float32)
    logits = np.log(p) - 50.
    with self.test_session():
      multinom = ds.Multinomial(total_count=3., logits=logits)
      self.assertEqual((1, 3), multinom.probs.get_shape())
      self.assertEqual((1, 3), multinom.logits.get_shape())
      self.assertAllClose(p, multinom.probs.eval())
      self.assertAllClose(logits, multinom.logits.eval())

  def testPmfandCountsAgree(self):
    p = [[0.1, 0.2, 0.7]]
    n = [[5.]]
    with self.test_session():
      dist = ds.Multinomial(total_count=n, probs=p, validate_args=True)
      dist.prob([2., 3, 0]).eval()
      dist.prob([3., 0, 2]).eval()
      with self.assertRaisesOpError("counts must be non-negative"):
        dist.prob([-1., 4, 2]).eval()
      with self.assertRaisesOpError("counts must sum to `self.total_count`"):
        dist.prob([3., 3, 0]).eval()

  def testPmfNonIntegerCounts(self):
    p = [[0.1, 0.2, 0.7]]
    n = [[5.]]
    with self.test_session():
      # No errors with integer n.
      multinom = ds.Multinomial(total_count=n, probs=p, validate_args=True)
      multinom.prob([2., 1, 2]).eval()
      multinom.prob([3., 0, 2]).eval()
      # Counts don't sum to n.
      with self.assertRaisesOpError("counts must sum to `self.total_count`"):
        multinom.prob([2., 3, 2]).eval()
      # Counts are non-integers.
      with self.assertRaisesOpError(
          "counts cannot contain fractional components."):
        multinom.prob([1.0, 2.5, 1.5]).eval()

      multinom = ds.Multinomial(total_count=n, probs=p, validate_args=False)
      multinom.prob([1., 2., 2.]).eval()
      # Non-integer arguments work.
      multinom.prob([1.0, 2.5, 1.5]).eval()

  def testPmfBothZeroBatches(self):
    with self.test_session():
      # Both zero-batches.  No broadcast
      p = [0.5, 0.5]
      counts = [1., 0]
      pmf = ds.Multinomial(total_count=1., probs=p).prob(counts)
      self.assertAllClose(0.5, pmf.eval())
      self.assertEqual((), pmf.get_shape())

  def testPmfBothZeroBatchesNontrivialN(self):
    with self.test_session():
      # Both zero-batches.  No broadcast
      p = [0.1, 0.9]
      counts = [3., 2]
      dist = ds.Multinomial(total_count=5., probs=p)
      pmf = dist.prob(counts)
      # 5 choose 3 = 5 choose 2 = 10. 10 * (.9)^2 * (.1)^3 = 81/10000.
      self.assertAllClose(81. / 10000, pmf.eval())
      self.assertEqual((), pmf.get_shape())

  def testPmfPStretchedInBroadcastWhenSameRank(self):
    with self.test_session():
      p = [[0.1, 0.9]]
      counts = [[1., 0], [0, 1]]
      pmf = ds.Multinomial(total_count=1., probs=p).prob(counts)
      self.assertAllClose([0.1, 0.9], pmf.eval())
      self.assertEqual((2), pmf.get_shape())

  def testPmfPStretchedInBroadcastWhenLowerRank(self):
    with self.test_session():
      p = [0.1, 0.9]
      counts = [[1., 0], [0, 1]]
      pmf = ds.Multinomial(total_count=1., probs=p).prob(counts)
      self.assertAllClose([0.1, 0.9], pmf.eval())
      self.assertEqual((2), pmf.get_shape())

  def testPmfCountsStretchedInBroadcastWhenSameRank(self):
    with self.test_session():
      p = [[0.1, 0.9], [0.7, 0.3]]
      counts = [[1., 0]]
      pmf = ds.Multinomial(total_count=1., probs=p).prob(counts)
      self.assertAllClose(pmf.eval(), [0.1, 0.7])
      self.assertEqual((2), pmf.get_shape())

  def testPmfCountsStretchedInBroadcastWhenLowerRank(self):
    with self.test_session():
      p = [[0.1, 0.9], [0.7, 0.3]]
      counts = [1., 0]
      pmf = ds.Multinomial(total_count=1., probs=p).prob(counts)
      self.assertAllClose(pmf.eval(), [0.1, 0.7])
      self.assertEqual(pmf.get_shape(), (2))

  def testPmfShapeCountsStretchedN(self):
    with self.test_session():
      # [2, 2, 2]
      p = [[[0.1, 0.9], [0.1, 0.9]], [[0.7, 0.3], [0.7, 0.3]]]
      # [2, 2]
      n = [[3., 3], [3, 3]]
      # [2]
      counts = [2., 1]
      pmf = ds.Multinomial(total_count=n, probs=p).prob(counts)
      pmf.eval()
      self.assertEqual(pmf.get_shape(), (2, 2))

  def testPmfShapeCountsPStretchedN(self):
    with self.test_session():
      p = [0.1, 0.9]
      counts = [3., 2]
      n = np.full([4, 3], 5., dtype=np.float32)
      pmf = ds.Multinomial(total_count=n, probs=p).prob(counts)
      pmf.eval()
      self.assertEqual((4, 3), pmf.get_shape())

  def testMultinomialMean(self):
    with self.test_session():
      n = 5.
      p = [0.1, 0.2, 0.7]
      dist = ds.Multinomial(total_count=n, probs=p)
      expected_means = 5 * np.array(p, dtype=np.float32)
      self.assertEqual((3,), dist.mean().get_shape())
      self.assertAllClose(expected_means, dist.mean().eval())

  def testMultinomialCovariance(self):
    with self.test_session():
      n = 5.
      p = [0.1, 0.2, 0.7]
      dist = ds.Multinomial(total_count=n, probs=p)
      expected_covariances = [[9. / 20, -1 / 10, -7 / 20],
                              [-1 / 10, 4 / 5, -7 / 10],
                              [-7 / 20, -7 / 10, 21 / 20]]
      self.assertEqual((3, 3), dist.covariance().get_shape())
      self.assertAllClose(expected_covariances, dist.covariance().eval())

  def testMultinomialCovarianceBatch(self):
    with self.test_session():
      # Shape [2]
      n = [5.] * 2
      # Shape [4, 1, 2]
      p = [[[0.1, 0.9]], [[0.1, 0.9]]] * 2
      dist = ds.Multinomial(total_count=n, probs=p)
      # Shape [2, 2]
      inner_var = [[9. / 20, -9 / 20], [-9 / 20, 9 / 20]]
      # Shape [4, 2, 2, 2]
      expected_covariances = [[inner_var, inner_var]] * 4
      self.assertEqual((4, 2, 2, 2), dist.covariance().get_shape())
      self.assertAllClose(expected_covariances, dist.covariance().eval())

  def testCovarianceMultidimensional(self):
    # Shape [3, 5, 4]
    p = np.random.dirichlet([.25, .25, .25, .25], [3, 5]).astype(np.float32)
    # Shape [6, 3, 3]
    p2 = np.random.dirichlet([.3, .3, .4], [6, 3]).astype(np.float32)

    ns = np.random.randint(low=1, high=11, size=[3, 5]).astype(np.float32)
    ns2 = np.random.randint(low=1, high=11, size=[6, 1]).astype(np.float32)

    with self.test_session():
      dist = ds.Multinomial(ns, p)
      dist2 = ds.Multinomial(ns2, p2)

      covariance = dist.covariance()
      covariance2 = dist2.covariance()
      self.assertEqual((3, 5, 4, 4), covariance.get_shape())
      self.assertEqual((6, 3, 3, 3), covariance2.get_shape())

  def testCovarianceFromSampling(self):
    # We will test mean, cov, var, stddev on a DirichletMultinomial constructed
    # via broadcast between alpha, n.
    theta = np.array([[1., 2, 3],
                      [2.5, 4, 0.01]], dtype=np.float32)
    theta /= np.sum(theta, 1)[..., None]
    # Ideally we'd be able to test broadcasting but, the multinomial sampler
    # doesn't support different total counts.
    n = np.float32(5)
    with self.test_session() as sess:
      dist = ds.Multinomial(n, theta)  # batch_shape=[2], event_shape=[3]
      x = dist.sample(int(250e3), seed=1)
      sample_mean = math_ops.reduce_mean(x, 0)
      x_centered = x - sample_mean[None, ...]
      sample_cov = math_ops.reduce_mean(math_ops.matmul(
          x_centered[..., None], x_centered[..., None, :]), 0)
      sample_var = array_ops.matrix_diag_part(sample_cov)
      sample_stddev = math_ops.sqrt(sample_var)
      [
          sample_mean_,
          sample_cov_,
          sample_var_,
          sample_stddev_,
          analytic_mean,
          analytic_cov,
          analytic_var,
          analytic_stddev,
      ] = sess.run([
          sample_mean,
          sample_cov,
          sample_var,
          sample_stddev,
          dist.mean(),
          dist.covariance(),
          dist.variance(),
          dist.stddev(),
      ])
      self.assertAllClose(sample_mean_, analytic_mean, atol=0., rtol=0.01)
      self.assertAllClose(sample_cov_, analytic_cov, atol=0., rtol=0.01)
      self.assertAllClose(sample_var_, analytic_var, atol=0., rtol=0.01)
      self.assertAllClose(sample_stddev_, analytic_stddev, atol=0., rtol=0.01)

  def testSampleUnbiasedNonScalarBatch(self):
    with self.test_session() as sess:
      dist = ds.Multinomial(
          total_count=5.,
          logits=math_ops.log(2. * self._rng.rand(4, 3, 2).astype(np.float32)))
      n = int(3e3)
      x = dist.sample(n, seed=0)
      sample_mean = math_ops.reduce_mean(x, 0)
      # Cyclically rotate event dims left.
      x_centered = array_ops.transpose(x - sample_mean, [1, 2, 3, 0])
      sample_covariance = math_ops.matmul(
          x_centered, x_centered, adjoint_b=True) / n
      [
          sample_mean_,
          sample_covariance_,
          actual_mean_,
          actual_covariance_,
      ] = sess.run([
          sample_mean,
          sample_covariance,
          dist.mean(),
          dist.covariance(),
      ])
      self.assertAllEqual([4, 3, 2], sample_mean.get_shape())
      self.assertAllClose(actual_mean_, sample_mean_, atol=0., rtol=0.07)
      self.assertAllEqual([4, 3, 2, 2], sample_covariance.get_shape())
      self.assertAllClose(
          actual_covariance_, sample_covariance_, atol=0., rtol=0.10)

  def testSampleUnbiasedScalarBatch(self):
    with self.test_session() as sess:
      dist = ds.Multinomial(
          total_count=5.,
          logits=math_ops.log(2. * self._rng.rand(4).astype(np.float32)))
      n = int(5e3)
      x = dist.sample(n, seed=0)
      sample_mean = math_ops.reduce_mean(x, 0)
      x_centered = x - sample_mean  # Already transposed to [n, 2].
      sample_covariance = math_ops.matmul(
          x_centered, x_centered, adjoint_a=True) / n
      [
          sample_mean_,
          sample_covariance_,
          actual_mean_,
          actual_covariance_,
      ] = sess.run([
          sample_mean,
          sample_covariance,
          dist.mean(),
          dist.covariance(),
      ])
      self.assertAllEqual([4], sample_mean.get_shape())
      self.assertAllClose(actual_mean_, sample_mean_, atol=0., rtol=0.07)
      self.assertAllEqual([4, 4], sample_covariance.get_shape())
      self.assertAllClose(
          actual_covariance_, sample_covariance_, atol=0., rtol=0.10)


if __name__ == "__main__":
  test.main()
