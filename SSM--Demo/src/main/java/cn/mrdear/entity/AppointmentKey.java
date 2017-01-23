package cn.mrdear.entity;

import javax.persistence.*;

@Table(name = "appointment")
public class AppointmentKey {
    /**
     * 图书ID
     */
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    /**
     * 学号
     */
    @Id
    @Column(name = "student_id")
    private Long studentId;

    /**
     * 获取图书ID
     *
     * @return book_id - 图书ID
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * 设置图书ID
     *
     * @param bookId 图书ID
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    /**
     * 获取学号
     *
     * @return student_id - 学号
     */
    public Long getStudentId() {
        return studentId;
    }

    /**
     * 设置学号
     *
     * @param studentId 学号
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}