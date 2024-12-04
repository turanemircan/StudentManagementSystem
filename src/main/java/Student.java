// domaın-entity - kalıcı hala getırmek ıstedıgımız durumlar bu class ta yapılır
public class Student {

    private Integer id; // burda none prımıtıve secmemızın sebebı primitive gırseydık varsayılan degerı 0 olurdu ben ılk deger atansın ıstemıyorum id bos gecilmesi durumunda bos oldugu bilinsin istiyorum

    private String name;

    private String lastName;

    private String city;

    private int age;
    // ogrencı notu gırılmemesi ve 0 alması durmunu ayırt etmek ıcın non-primitive kullanılması daha mantıklı - yanı fild anlamına göre tür belirlenir

    // parametreli/parametresiz cons

    public Student() {
    }

    public Student(String name, String lastName, String city, int age) {
        this.name = name;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
    }

    // getter-setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", age=" + age +
                '}';
    }
}
