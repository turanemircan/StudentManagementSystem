import java.util.Scanner;

// Uygulamayı başlatan ve (controller:istemci ile iletişime geçilecek)
// controller : işlemci ya da client ile iletişime gecicek
public class Runner {

    public static Scanner inp = new Scanner(System.in);

    public static void main(String[] args) {
        start();
    }

    private static void start() {

        StudentService service = new StudentService();
        // 7-C: uygulama başlatıldığında tablo oluşturulsun
        service.createStudentTable();

        int select;
        int id;

        do {

            System.out.println("=========================================");
            System.out.println("Öğrenci Yönetim Paneli");
            System.out.println("1-Öğrenci Kaydetme");
            System.out.println("2-Tüm Öğrencileri Listeleme");
            System.out.println("3-Öğrenciyi Güncelleme");
            System.out.println("4-Öğrenciyi silme");
            System.out.println("5-Tek bir Öğrenciyi görüntüleme");
            System.out.println("6-Tüm Öğrencilerin AD-SOYAD bilgilerini rapora yazdırma");
            System.out.println("0-ÇIKIŞ");
            System.out.print("İşlem Seçiniz : ");
            select = inp.nextInt();
            inp.nextLine(); // Bunu sonradan bir okuma daha yaparız dıye her zaman okuma işlemlerinden sonra bu kodu yazmak buffer boşaltımı için önemli

            switch (select) {
                case 1:
                    // Bilgileri verilen öğrenciyi kaydetme
                    Student newStudent = service.getStudentInfo();
                    new Thread(() -> {
                        service.saveStudent(newStudent);
                    }).start();
                    break;
                case 2:
                    // Ogrencileri konsola yazdırma
                    service.printAllStudent();
                    break;
                case 3:
                    // Id si verilen öğrenciyi güncelleme
                    id = getIdInfo();
                    service.updateStudentById(id);
                    break;
                case 4:
                    //id si verilen öğrenciyi silme
                    id = getIdInfo();
                    service.deleteStudentById(id);
                    break;
                case 5:
                    //id si verilen öğrenciyi görüntüleme
                    id = getIdInfo();
                    service.printStudentById(id);
                    break;
                case 0:
                    System.out.println("İyi Günler...");
                    break;
                default:
                    System.out.println("Hatalı Giriş!");
                    break;
            }
        } while (select != 0);
    }

    private static int getIdInfo() {
        System.out.println("Öğrenci id: ");
        int id = inp.nextInt();
        inp.nextLine();
        return id;
    }
}
