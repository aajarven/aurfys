# -*- coding: utf-8 -*-
"""
Created on Fri Mar 25 22:42:36 2016

@author: anni
"""

import matplotlib.pyplot as plt
import numpy as np
from math import pi,sin,cos,tan,log,radians

alpha = range(180)
ls = np.zeros(len(alpha)) # lommel-seeliger
l = np.zeros(len(alpha)) # lambert
alpha = np.add(alpha, 0.01)

alpha = [radians(a) for a in alpha]

ls0 = (1-sin(0.5*alpha[0])*tan(0.5*alpha[0]))
l0 = pi

for i in range(len(alpha)): # ei käydä ekaa läpi
    ls[i] = (1-sin(0.5*alpha[i])*tan(0.5*alpha[i])*log(1.0/tan(0.25*alpha[i])))/ls0
    l[i] = sin(alpha[i])+(pi-alpha[i])*cos(alpha[i])/l0
    

plt.plot(alpha, ls)
plt.plot(alpha, l)
plt.show()
    