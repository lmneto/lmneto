l = [7, 8, 9, 2, 3, 1, 4, 10, 5, 6]

print("list:",l)

#sorting list using .sort()
l.sort()

#print sorted list
print("sorted list: ",l)

#print reverse sorted list using .sorted()
print("reverse: ",sorted(l, reverse=True))

#odd numb
print("odd: ",l[::2])

#even numb
print("even: ",l[1::2])

#multiples of 3
print("multiples of 3: ",l[2::3])
