import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// servıce: bussıness manttiksal lojik ıslemleri burda yapılır
// service class ları repo classları ıle görüşür
public class StudentService {
    Scanner inp = Runner.inp;  // runnerda tanımladığımızı burda kullanmış olduk

    // repositorynin methodlarını kullanabılmek için obje oluşturalım
    private Repository repo = new StudentRepository(); // burada başka constroctor lar oluşturabiliriz

    // 7-a student tablosunu oluşturma
    public void createStudentTable() {
        repo.createTabLe();
    }

    // 8-a verilen bilgiler ile ogrenciyi kaydetmek
    public Student getStudentInfo() {
        System.out.print("AD : ");
        String name = inp.nextLine();
        System.out.print("SOYAD : ");
        String lastname = inp.nextLine();
        System.out.print("ŞEHİR : ");
        String city = inp.next();
        System.out.println("YAŞ : ");
        int age = inp.nextInt();
        inp.nextLine();
        return new Student(name, lastname, city, age); // id otomatik olusturulu null olarak duruyor bir ekleme yapilmadigi icin
    }

    public void saveStudent(Student newStudent) {
        repo.save(newStudent);
    }

    // 9-a tüm öğrencileri listeleme
    public void printAllStudent() {
        List<Student> students = repo.findAll();
        System.out.println("================Tüm öğrencileri Listeleme===============");
        for (Student student : students) {
            System.out.println("id : " + student.getId() +
                    " Ad : " + student.getName() +
                    " Soyad : " + student.getLastName() +
                    " Şehir : " + student.getCity() +
                    " Yaş : " + student.getAge());
        }
    }

    // 10-a-id si verilen öğrenciyi getirme
    public Student getStudentById(int id) {
        Student student = (Student) repo.findById(id);
        return student;
    }

    // 10-b idsi verilen öğrencinin bilgilerini yazdırma
    public void printStudentById(int id) {
        Student foundStudent = getStudentById(id);
        if (foundStudent == null) {
            System.err.println("İd'si verilen öğrenci bulunumadı!!!!");
        } else {
            System.out.println(foundStudent);
        }
    }

    // 11-a- id si verilen öğrencinin bilgilerini yeni bilgiler ile değiştirme
    public void updateStudentById(int id) {
        // Bu id ile öğrenci var mı?
        Student foundStudent = getStudentById(id);
        if (foundStudent == null) {
            System.out.println("ID si verilen öğrenci bulunamadı. ID : " + id);
        } else { // harry potter
            // girilecek yeni bilgiler nedir?
            Student newStudent = getStudentInfo();
            // var olan öğrencinin bilgilerini
            // yeni öğrenci ile değiştirelim
            foundStudent.setName(newStudent.getName());
            foundStudent.setLastName(newStudent.getLastName());
            foundStudent.setCity(newStudent.getCity());
            foundStudent.setAge(newStudent.getAge());
            // id'si aynı kalacak
            // foundstudent:name:yeni lastname:yeni city:yeni age:yeni İd:eski
            repo.update(foundStudent);
        }
    }

    // 12-a- id si verilen öğrenciyi silme
    public void deleteStudentById(int id) {
        repo.deleteById(id);
    }

    //13- tüm öğrencilerin ad-soyad bilgilerini rapora yazdırma
    public void generateReport() {

        List<Student> allStudents = repo.findAll();
        System.err.println("Report is loading...");
        try {
            Thread.sleep(10000);
            FileWriter writer = new FileWriter("student_report.txt");
            writer.write("*** Student Report ***\n");
            writer.write("------------------------\n");
            for (Student student : allStudents) {
                writer.write("Ad : " + student.getName() + " Soyad : " + student.getLastName() + "\n");
            }
            writer.close();
            System.err.println("Report generated and printed to student_report.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
