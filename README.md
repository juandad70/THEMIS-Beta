Themis - Sistema Integral de Gestión de Novedades y Procesos
Descripción
Themis es una aplicación web diseñada para gestionar novedades y procesos administrativos de aprendices. El sistema facilita el control de novedades, la automatización de flujos de trabajo, la visualización de procesos, y la generación de reportes. Además, permite la gestión de roles con acceso restringido a funcionalidades críticas.
La solución está compuesta por un frontend desarrollado con Next.js y Tailwind CSS, y un backend basado en Spring Boot. El sistema se integra con diversas APIs REST para asegurar una experiencia fluida y eficiente en el manejo de datos.
Funcionalidades Principales
•	Gestión de Novedades: Registro, seguimiento y actualización de novedades de los aprendices.
•	Automatización de Procesos: Automatización de flujos de trabajo según la respuesta de los aprendices en el sistema.
•	Módulo de Reportes: Generación de reportes en formato PDF sobre novedades por aprendiz, período o estado.
•	Control de Roles y Permisos: Gestión de roles de usuarios como Administrador, Coordinador, y Aprendiz.
•	Carga de Archivos: Posibilidad de cargar y visualizar archivos adjuntos a los registros de novedades.
•	Sistema de Notificaciones: Envío automático de notificaciones por correo electrónico a los usuarios.
Arquitectura Tecnológica
Backend (Spring Boot)
•	Lenguaje: Java 17+
•	Framework: Spring Boot
•	Base de Datos: MySQL o PostgreSQL
•	Dependencias Clave:
o	Spring Data JPA para la persistencia
o	Spring Security para la gestión de autenticación y roles
o	JavaMailSender para el envío de correos electrónicos
o	JasperReports para la generación de reportes
Frontend (Next.js)
•	Lenguaje: JavaScript / TypeScript
•	Framework: Next.js
•	Estilos: Tailwind CSS
•	Librerías Clave:
o	Axios para la comunicación HTTP con el backend
o	React ApexCharts para la visualización de datos en gráficos
o	react-toastify para el manejo de notificaciones
o	Html2Canvas y JSPDF para la generación y exportación de PDF
Requisitos del Sistema
Backend
•	Java 17+
•	Maven para la gestión de dependencias
•	Base de datos relacional (MySQL o PostgreSQL)
Frontend
•	Node.js 18+
•	npm (o Yarn) para la gestión de dependencias
Configuración del Entorno
Backend
1.	Configura las variables de conexión a la base de datos y detalles del servidor de correos electrónicos en application.properties o application.yml:
properties
Copiar código
spring.datasource.url=jdbc:mysql://localhost:3306/themis
spring.datasource.username=root
spring.datasource.password=tu-contraseña

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu-email@gmail.com
spring.mail.password=tu-contraseña
2.	Ejecuta el proyecto con Maven:
bash
Copiar código
mvn spring-boot:run
Frontend
1.	Clona el repositorio del frontend:
bash
Copiar código
git clone https://github.com/usuario/themis-frontend.git
2.	Instala las dependencias:
bash
Copiar código
npm install
3.	Configura las variables de entorno en .env.local:
env
Copiar código
NEXT_PUBLIC_API_URL=http://localhost:8080/api
4.	Inicia el servidor de desarrollo:
bash
Copiar código
npm run dev
Estructura del Proyecto
Backend
•	/src/main/java/com/themis: Código fuente del backend.
o	controllers/: Controladores para gestionar las solicitudes HTTP.
o	services/: Lógica de negocio y servicios para el manejo de entidades.
o	repositories/: Interfaces para interactuar con la base de datos.
o	models/: Entidades y DTOs utilizados en la aplicación.
Frontend
•	/src: Código fuente del frontend.
o	components/: Componentes reutilizables de la interfaz de usuario.
o	pages/: Rutas y vistas principales de la aplicación.
o	services/: Módulos para la comunicación con las APIs del backend.
Endpoints API
Autenticación y Roles
•	POST /api/auth/login: Autenticar usuario en el sistema.
•	GET /api/roles: Listar los roles disponibles.
Novedades
•	POST /api/novelty: Crear una nueva novedad.
•	GET /api/novelty/{id}: Obtener detalles de una novedad específica.
Reportes
•	GET /api/report/generate: Generar un reporte PDF basado en los filtros seleccionados.
Agradecimientos
El equipo de desarrollo de Themis agradece la colaboración de instructores, aprendices y todos aquellos que brindaron su apoyo y sugerencias para la mejora continua de este proyecto.
Contribuciones
Si deseas contribuir a Themis, ya sea reportando errores, sugiriendo nuevas funcionalidades o colaborando en el código, por favor, abre un issue o envía un pull request. Valoramos cada aporte que ayuda a mejorar el sistema.

