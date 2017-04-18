#!/usr/bin/python
#encoding=utf-8  
  
import smtplib  
from email.mime.text import MIMEText  
from email.mime.image import MIMEImage  
from email.mime.multipart import MIMEMultipart  
import sys

if len(sys.argv) ==2:
        imageFile=sys.argv[1]
        print imageFile
else:
        print "please add one params,eg: ./getMail.py filepath!!"
        sys.exit(0) 

if __name__ == '__main__':  
        fromaddr = 'jianyikai@kaolazhengxin.com'  
        toaddrs = ['297208236@qq.com','jianyikai@kaolazhengxin.com']  
        subject = imageFile.split('/')[-1] 
  
        content = imageFile  
        textApart = MIMEText(content)  
  
        #imageFile = 'D:/py/available.txt'  
        imageApart = MIMEImage(file(imageFile, 'rb').read(), imageFile.split('.')[-1])  
        imageApart.add_header('Content-Disposition', 'attachment', filename=imageFile.split('/')[-1])  
  
        m = MIMEMultipart()  
        m.attach(textApart)  
        m.attach(imageApart)  
        m['Subject'] = subject  
  
        server = smtplib.SMTP('smtp.kaolazhengxin.com')  
        server.login(fromaddr,'Klzx@202')  
        server.sendmail(fromaddr, toaddrs, m.as_string())  
        server.quit()
        print subject + " is sended"
