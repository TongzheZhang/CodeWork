# -*- coding: utf-8 -*-
"""
Created on Mon Jun 26 16:53:57 2017

@author: richard
"""

import pandas as pd

train = pd.read_csv('labeledTrainData.tsv', '\t')
#print train.info()
test = pd.read_csv('testData.tsv', '\t')
#print test.head()
from bs4 import BeautifulSoup
import re
#natural language toolkit
from nltk.corpus import stopwords
