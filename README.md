# CLINIC API REST Stateless

### Guide
The following guides illustrate how to use some features concretely:

Business rules:
* Clinic working hours are Mondays to Saturdays from 7am to 7pm.
* Appointments are booked not later than 30 minutes before the start of it.
* Patients are allowed to book 1 appointment per day.
* If the physician's ID is not known, providing a Specialty will make the system provide you a random available professional.

<hr>

![img_1.png](img_1.png)
<hr>
Authenticate using an email and a password. The password will be encrypted
using bcrypt and compared to the one stored in the postgres DB.

![img_2.png](img_2.png)
<hr>
Then you can add, list and delete appointments:

![img_3.png](img_3.png)
<hr>
Also register, update, list and delete patients from the database:

![img_4.png](img_4.png)
<hr>
And as well with physicians:

![img_5.png](img_5.png)
<hr>








