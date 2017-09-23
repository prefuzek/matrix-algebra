# matrix-algebra

## Overview

This is an as-yet-unnamed computational software project. My goals were to create a functional calculator for linear algebra and more,
and to learn more about the Java language and computational models.

## Features

There are currently two views: the matrix calculator and the algebra simplifier.

### Matrix Calculator

- Add, subtract, multiply, and invert matrices of any size
- Calculate determinants
- Input can be mathematical expressions, e.g. `1+(2\3)(4-1)` will be evaluated as 3
- Fractional values are accepted, and fully reduced in the result

### Algebra Simplifier

- Interprets letters as variables, allowing you to create polynomials in any number of variables
- Add, subtract, multiply, and exponentiate any polynomial expression (polynomial division is not yet supported)
- Results are fully expanded and simplified

## Design

The classes for rational numbers, matrices, and polynomials, as well as the methods for manipulating them, are completely self-developed. I wanted to use this opportunity to really
get into the guts of basic computational mathematics (it also has the benefit of giving me extreme freedom in how I want to proceed). Since
the project is for my own learning, layout and visual design has taken a backseat (as you can tell).
