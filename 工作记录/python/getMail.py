#!/usr/bin/python
#coding=utf-8
import poplib,email
from email import parser

host='pop.kaolazhengxin.com'
username='jianyikai@kaolazhengxin.com'
password='Klzx@202'

pop_con=poplib.POP3(host)
pop_con.user(username)
pop_con.pass_(password)

ret=pop_con.list()
num=len(ret[1])

for i in range(num):
  if i < int(num-1):
     continue
  m=pop_con.retr(i+1)
  msg = email.message_from_string('\n'.join(m[1]))
  
  for part in msg.walk():
    contenttype=part.get_content_type()
    filename=part.get_filename()
    
    if filename:
       data=part.get_payload(decode=True)
       file("mail%d.attach.%s" %(i+1,filename),'wb').write(data)
   
    if i == int(num-1) and  contenttype == 'text/plain':
      data=part.get_payload(decode=True)
      print data 
pop_con.quit()
