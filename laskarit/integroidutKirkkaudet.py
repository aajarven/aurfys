# -*- coding: utf-8 -*-
"""
Created on Fri Mar 25 22:42:36 2016

@author: anni
"""

import matplotlib.pyplot as plt
import numpy as np
from math import pi,sin,cos,tan,log,radians,exp

alpha = range(180)
ls = np.zeros(len(alpha)) # lommel-seeliger
l = np.zeros(len(alpha)) # lambert
fiiPieni = np.zeros(len(alpha))
fiiIso = np.zeros(len(alpha))
gPieni = 0.1
gIso = 0.4
alpha = np.add(alpha, 0.01)

alpha = [radians(a) for a in alpha]

ls0 = (1-sin(0.5*alpha[0])*tan(0.5*alpha[0]))
l0 = pi


for i in range(len(alpha)): # ei käydä ekaa läpi
    ls[i] = (1-sin(0.5*alpha[i])*tan(0.5*alpha[i])*log(1.0/tan(0.25*alpha[i])))/ls0
    l[i] = (sin(alpha[i])+(pi-alpha[i])*cos(alpha[i]))/l0
    fiiPieni[i] = (1.0-gPieni)*exp(-3.33*tan(0.5*alpha[i])**0.63)+gPieni*exp(-1.87*tan(0.5*alpha[i])**1.22)
    fiiIso[i] = (1.0-gIso)*exp(-3.33*tan(0.5*alpha[i])**0.63)+gIso*exp(-1.87*tan(0.5*alpha[i])**1.22)

lsplot, = plt.plot(alpha, ls, label = "Lommel-Seeliger")
lplot, = plt.plot(alpha, l, label = "Lambert")
fiipPlot, = plt.plot(alpha, fiiPieni, label = "matala albedo")
fiiiPlot, = plt.plot(alpha, fiiIso, label = "korkea albedo")
plt.xlabel("alpha (rad)")
plt.legend([lsplot, lplot, fiipPlot, fiiiPlot], ["Lommel-Seeliger", "Lambert", "matala albedo", "korkea albedo"])
axes = plt.gca()
axes.set_xlim([0,pi])
plt.show()
plt.savefig("teht6.png")
    