#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Fri Jan 13 15:31:38 2017
Kaggle Titanic
@author: tongzhezhang
"""
import pandas as pd

train = pd.read_csv('train.csv')
test = pd.read_csv('test.csv')

print train.info()
print test.info()

selected_features=['Pclass','Sex','Age','Embarked','SibSp','Parch','Fare']

X_train = train[selected_features]
X_test = test[selected_features]

y_train = train['Survived']
#get # of featrue Embarked
print X_train['Embarked'].value_counts()
print X_test['Embarked'].value_counts()

#fill the NAN features
X_train['Embarked'].fillna('S', inplace=True)
X_test['Embarked'].fillna('S', inplace=True)

X_train['Age'].fillna(X_train['Age'].mean(), inplace=True)
X_test['Age'].fillna(X_test['Age'].mean(), inplace=True)
X_test['Fare'].fillna(X_test['Fare'].mean(), inplace=True)
#check again
print X_train.info()
print X_test.info()
#DictVectorizer
from sklearn.feature_extraction import DictVectorizer
dict_vec = DictVectorizer(sparse=False)
X_train = dict_vec.fit_transform(X_train.to_dict(orient='record'))
print dict_vec.feature_names_
X_test = dict_vec.transform(X_test.to_dict(orient='record'))

# set model as RondomForestClassifier
from sklearn.ensemble import RandomForestClassifier
# init
rfc = RandomForestClassifier()
# validation
from sklearn.cross_validation import cross_val_score
# in-sample
print 'correct percent:',cross_val_score(rfc, X_train, y_train, cv=5).mean()

rfc.fit(X_train,y_train)
rfc_y_predict = rfc.predict(X_test)
rfc_submission = pd.DataFrame({'PassengerId':test['PassengerId'], 'Survived':rfc_y_predict})
# save as a csv
rfc_submission.to_csv('rfc_submission',index=False)