# -*- coding: utf-8 -*-
"""
Created on Fri Jul 07 13:29:17 2017

@author: Richard10ZTZ

example_1.py

Just a short example showing synchronous running of 'tasks'
"""

import queue

def task(name, work_queue):
    if work_queue.empty():
        print 'Task ',name,'nothing to do'
    else:
        while not work_queue.empty():
            count = work_queue.get()
            total = 0
            for x in range(count):
                print'Task', name, 'running'
                total += 1
            print 'Task',name,'total:',total


def main():
    """
    This is the main entry point for the program
    """
    # create the queue of 'work'
    work_queue = queue.Queue()

    # put some 'work' in the queue
    for work in [15, 10, 5, 2]:
        work_queue.put(work)

    # create some tasks
    tasks = [
        (task, 'One', work_queue),
        (task, 'Two', work_queue)
    ]

    # run the tasks
    for t, n, q in tasks:
        t(n, q)

if __name__ == '__main__':
    main()