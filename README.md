# HR Buddy

HR Buddy is an HR Management System is a comprehensive application for managing various aspects of human resources, including employee attendance, department management, vacation tracking, and job management. The system provides both RESTful and SOAP web services for easy integration with other systems.

## Technologies Used

- Java
- JAX-RS (Java API for RESTful Web Services)
- JAX-WS (Java API for XML Web Services)
- Maven
- Apache Tomcat (or any other Java EE application server)

## Features

The HR Management System provides a range of features to help manage human resources effectively:

1. **Employee Management**: Easily manage employee information, including details such as name, position, department, and contact information.

2. **Attendance Tracking**: Track and manage employee attendance records, including clock-in/clock-out times, leave requests, and attendance history.

3. **Department Management**: Manage organizational departments, including creating, updating, and deleting departments, as well as assigning employees to departments.

4. **Vacation Tracking**: Track employee vacation requests and manage vacation schedules, ensuring smooth operation and adequate staffing.

5. **Job Management**: Manage job positions within the organization, including creating new positions, updating existing positions, and managing job descriptions.

6. **Security**: Ensure secure access to the system with role-based access control, allowing administrators to manage user permissions and access levels.

7. **Integration**: Integrate with other systems using both RESTful and SOAP web services, allowing seamless data exchange and interoperability.

8. **Scalability**: Designed to be scalable, allowing the system to grow with the organization's needs and handle increasing amounts of data and users.

9. **Customization**: Easily customizable to suit the specific needs of your organization, with flexible configuration options and extensible functionality.


## How to Run

1. Clone the repository.
2. Build the project using Maven: `mvn clean package`.
3. Deploy the generated WAR file to your Apache Tomcat server.
4. Access the RESTful services using their respective endpoints (e.g., `http://localhost:9090/hrbuddy/v1/{endpoint}`).
5. Access the SOAP services using their respective WSDL URLs (e.g., `http://localhost:9090/hrbuddy/soap/{endpoint}?wsdl`).


## Documentations

- [REST](https://documenter.getpostman.com/view/19754707/2sA3BgAaZv)
- [SOAP](https://documenter.getpostman.com/view/19754707/2sA3BgBveU)

