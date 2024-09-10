![Java](https://img.shields.io/badge/Spring_Framework-Java-orange?style=for-the-badge&logo=springboot
)

# Projeto Java backend para um blog

##### **ArticOS** é uma idéia pessoal de uma espécie de diário público, onde os usuários poderão trocar informações sobre o que aprendem no dia a dia.

### Aplicação feita com Spring Framework, utilizando gradle e as seguintes dependências:
- ✅ Spring Security
- ✅ Spring JPA
- ✅ Spring Web
- ✅ JWT
- ✅ Lombok
- ✅ PostgreSQL
---  

## Os endpoints são: 
_A role ADMIN tem acesso a todos os endpoints_

---
![Static Badge](https://img.shields.io/badge/GET-green?style=flat-square) 
**Sem Autenticação**  
api/v1/get  

**Com Autenticação USER / ADMIN**  
user/getMyAccount  
post/all

**Com Autenticação ADMIN**  
admin/getAllUsers  
admin/getUser | @PathVariable  
  
---  

![Static Badge](https://img.shields.io/badge/POST-yellow?style=flat-square)
**Sem Autenticação**  
auth/register | @RequestBody:
```
{
"name": "",
"password": ""
}
```
auth/login | @RequestBody:
```
{
"name": "",
"password": ""
}
``` 
**Com Autenticação USER / ADMIN**  
post/create | @RequestBody: 
```
{
"title": "",
"imgUrl": "",
"content": ""
}
```  
---
![Static Badge](https://img.shields.io/badge/DELETE-red?style=flat-square)
**Sem Autenticação**  
//

**Com Autenticação USER / ADMIN**  
user/deleteMyAccount  
post/delete/{id} | @PathVariable


**Com Autenticação ADMIN**  
admin/deleteUser/{id} | @PathVariable  
admin/deletePost/{id} | @PathVariable  

---
![Static Badge](https://img.shields.io/badge/PUT-blue?style=flat-square)
**Sem Autenticação**  
//

**Com Autenticação USER / ADMIN**  
user/changeMyPassword | @RequestBody:
```
{
"newPassword": "",
"confirmPassword": ""
}
```  
--- 

No mais, é isso.  
Abraços 🤘!

---
_É meu primeiro projeto próprio, ainda estou adaptando as lógicas da aplicação conforme faço o projeto._
