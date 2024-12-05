import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//repository:veritabanı ile ilgili işlemler
//create table , record, update, delete, findAll
//student, instructor,course .... gibi başka başka alanlarda olabilir
// bu yapılacakların herkesin yaptıgı işleri standarlaştırmak inin interface tanımlama ve kurala bağlamak daha güzel olur

public class StudentRepository implements Repository<Student, Integer> {

    // 7-t_student tablosunu olusturma
    @Override
    public void createTabLe() {
        JdbcUtils.setConnection(); // bağlantı olustu
        JdbcUtils.setStatement(); // statement başlattık

        try {
            JdbcUtils.st.execute("CREATE TABLE IF NOT EXISTS t_student(\n" +
                    "id SERIAL UNIQUE,\n" +
                    "name VARCHAR(50) NOT NULL CHECK(LENGTH(name)>0),\n" +
                    "lastname VARCHAR(50) NOT NULL CHECK(LENGTH(name)>0),\n" +
                    "city VARCHAR(50) NOT NULL, \n" +
                    "age INTEGER NOT NULL CHECK(age>0)  )");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                JdbcUtils.st.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 8-öğrenciyi kaydetme
    @Override
    public void save(Student student) {
        JdbcUtils.setConnection();
        JdbcUtils.setPreparedStatement("INSERT INTO t_student(name, lastname, city, age) VALUES(?,?,?,?)");
        try {
            JdbcUtils.prst.setString(1, student.getName());
            JdbcUtils.prst.setString(2, student.getLastName());
            JdbcUtils.prst.setString(3, student.getCity());
            JdbcUtils.prst.setInt(4, student.getAge());
            JdbcUtils.prst.executeUpdate();
            System.out.println("------Basarili Bir Sekilde Kaydedildi------");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                JdbcUtils.prst.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 9-tüm öğrencileri db den çekme
    public List<Student> findAll() {
        JdbcUtils.setConnection();
        JdbcUtils.setStatement();
        List<Student> allStudent = new ArrayList<>();
        try {
            ResultSet rs = JdbcUtils.st.executeQuery("SELECT * FROM t_student");
            while (rs.next()) {
                Student student = new Student(rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("city"),
                        rs.getInt("age"));
                student.setId(rs.getInt("id"));
                allStudent.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JdbcUtils.st.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return allStudent;
    }

    // 11- verilen yeni öğrenci bilgisi ile var olan öğrenciyi güncelleme
    @Override
    public void update(Student student) { // student: öğrencinin yeni bilgileri
        JdbcUtils.setConnection();
        JdbcUtils.setPreparedStatement("UPDATE t_student SET name=?, lastname=?, city=?, age=? WHERE id=?");
        try {
            JdbcUtils.prst.setString(1, student.getName());
            JdbcUtils.prst.setString(2, student.getLastName());
            JdbcUtils.prst.setString(3, student.getCity());
            JdbcUtils.prst.setInt(4, student.getAge());
            JdbcUtils.prst.setInt(5, student.getId());
            int updated = JdbcUtils.prst.executeUpdate();
            if (updated > 0) {
                System.out.println("Güncelleme başarılı...:"); // 1
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                JdbcUtils.prst.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 12-id si verilen öğrenciyi silme
    @Override
    public void deleteById(Integer id) {
        JdbcUtils.setConnection();
        JdbcUtils.setStatement();

        try {
            int deleted = JdbcUtils.st.executeUpdate("DELETE FROM t_student WHERE id=" + id);
            // kayıt bulunursa 1 aksi halde 0 kayıt silinir
            if (deleted > 0) {
                System.out.println("Öğrenci silindi, ID : " + id);
            } else {
                System.out.println("Öğrenci bulunamadı....");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                JdbcUtils.st.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 10-idsi verilen öğrenciyi bulma
    @Override
    public Student findById(Integer id) {
        Student student = null;
        JdbcUtils.setConnection();
        JdbcUtils.setPreparedStatement("SELECT * FROM t_student WHERE id=?");
        try {
            JdbcUtils.prst.setInt(1, id);
            ResultSet rs = JdbcUtils.prst.executeQuery();
            if (rs.next()) {
                student = new Student(rs.getString("name"), rs.getString("lastname"),
                        rs.getString("city"), rs.getInt("age"));
                student.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                JdbcUtils.prst.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return student;
    }
}