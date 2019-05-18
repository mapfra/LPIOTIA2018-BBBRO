# -*- coding: utf-8 -*-
"""
Created on Thu May 16 15:18:08 2019

@author: ms812264

Requête HHTP sur le port om2m
reception des data de type 4 correspondant a la detection
"""
import requests
url = "http://10.0.4.12:8080/~/mn-cse?fu=1&ty=4"

r = requests.get(url, headers={'X-M2M-Origin': 'admin:admin', 'Accept' : 'application/json'})
print(r.text)

