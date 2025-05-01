package com.universitatea.iterator;

import com.universitatea.entity.Student;
import com.universitatea.exception.ResourceNotFoundException;

import java.util.List;

public class StudentIterator implements Iterator<Student> {
    private List<Student> students;
    private int currentIndex = 0;

    public StudentIterator(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < students.size();
    }

    @Override
    public Student next() {
        if (hasNext()) {
            return students.get(currentIndex++);
        }
        throw new ResourceNotFoundException("No more students in the group");
    }
}

