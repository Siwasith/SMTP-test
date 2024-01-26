import java.util.*
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

fun sendEmail(to: String, subject: String, body: String) {
    // Set the properties for connecting to Gmail SMTP
    val properties = Properties()
    //ระบุเซิร์ฟเวอร์ SMTP ที่จะใช้ในการส่งอีเมล์. สำหรับ Microsoft Outlook, คุณต้องให้ค่าเป็นที่อยู่ของเซิร์ฟเวอร์ SMTP ของ Microsoft Exchange.
    properties["mail.smtp.host"] = "smtp.gmail.com"
    //ระบุพอร์ตที่ใช้สำหรับการเชื่อมต่อ SMTP. สำหรับ Microsoft Exchange, พอร์ตทั่วไปที่ใช้คือ 587
    properties["mail.smtp.port"] = "587"
    //ระบุว่าต้องการเปิดใช้งานการรับรองตัวตนหรือไม่. ในที่นี้, เปิดใช้งานการรับรองตัวตน
    properties["mail.smtp.auth"] = "true"
    //ระบุว่าต้องการเปิดใช้งาน StartTLS หรือไม่. StartTLS เป็นโปรโตคอลที่ให้ความปลอดภัยในการเชื่อมต่อ
    properties["mail.smtp.starttls.enable"] = "true"

    val username = ""
    val password = ""

    /* Create Session
    ใช้ Session.getInstance เพื่อสร้าง session สำหรับการเชื่อมต่อกับเซิร์ฟเวอร์อีเมล์
    ภายใน Session.getInstance คุณได้ระบุ properties ที่เป็นการกำหนดค่าสำหรับการเชื่อมต่อ
    และกำหนด Authenticator ที่ใช้สำหรับการรับรองตัวตน
    การสร้าง Authenticator ทำในลักษณะการสร้างคลาสภายในภาษา Kotlin
    โดยใช้ anonymous class (คลาสที่ไม่มีชื่อ) ที่ extends javax.mail.Authenticator.
    ใน anonymous class นี้มีการโอเวอร์ไรด์เมธอด getPasswordAuthentication
    เพื่อให้คืน PasswordAuthentication ที่ใส่ชื่อผู้ใช้และรหัสผ่าน.*/
    val session = Session.getInstance(properties, object : javax.mail.Authenticator() {
        override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
            return javax.mail.PasswordAuthentication(username, password)
        }
    })
    /*สร้าง Message: โค้ดนี้ใช้ MimeMessage เพื่อสร้างอ็อบเจกต์ที่เป็นข้อมูลของอีเมล์.
    จากนั้นกำหนดผู้ส่ง (From), ผู้รับ (To), หัวข้อ (Subject), และข้อความ (Text body) ของอีเมล์.*/
    try {
        // Create message
        val message = MimeMessage(session)

        //บรรทัดนี้ใช้เพื่อกำหนดผู้ส่ง (Sender) ของอีเมล์ที่กำลังถูกสร้างในอ็อบเจกต์ message.
        message.setFrom(InternetAddress(username))

        //Message.RecipientType.TO: ระบุว่าผู้รับที่กำลังถูกกำหนดคือผู้รับหลัก (To)
        //นอกจากนี้ยังมี Message.RecipientType.CC (ผู้รับที่ถูก Carbon Copy)
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))

        //ใช้เพื่อกำหนดหัวข้อ (subject) ของอีเมล์ที่กำลังถูกสร้าง:
        message.subject = subject

        //ใช้เพื่อกำหนดข้อความ (body) ของอีเมล์ที่กำลังถูกสร้าง:
        message.setText(body)

        // Send the email
        Transport.send(message)
        println("Email sent successfully.")
    } catch (e: Exception) {
        //การแสดง StackTrace ที่เกิดขึ้น (ด้วย e.printStackTrace()) ช่วยในการตรวจสอบและแก้ไขปัญหาที่เกิดขึ้น.
        e.printStackTrace()
    }
}

fun main() {
    val to = "test.gmail.com"
    val subject = "test"
    val body = "test"

    sendEmail(to, subject, body)
}
