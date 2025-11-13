# Titulo
TP Cloud Backend

## Descripción
Backend del TP de Cloud (4to año) 2025

Consiste en una inmobiliaria completa, un crud, login, seguridad de spring security, y asistencia de IA al mirar las propiedades. La IA puede estar limitada ya que utilizamos la version gratis de la api de openrouter.ai

# Debug / Production
- Modificar en application.properties segun la necesidad:
    `spring.profiles.active=dev`
    `spring.profiles.active=prod`

Cuando esta en modo dev, usa la DB en memoria h2, por lo que no hay que preocuparse por correr contenedores de bases de datos.

## Compilar para producción
- Posicionarse en la carpeta raíz
```bash
sudo docker build -t backend .
```

<img width="1920" height="1080" alt="Screenshot from 2025-08-22 21-03-22" src="https://github.com/user-attachments/assets/03a30956-f60f-4e7d-902c-dc45cf189750" />
<img width="1920" height="1080" alt="Screenshot from 2025-08-22 21-03-10" src="https://github.com/user-attachments/assets/2802bf48-2d4c-48f6-afe9-4aa869810ae4" />
<img width="1920" height="1016" alt="Screenshot from 2025-08-22 21-02-59" src="https://github.com/user-attachments/assets/b934653e-2162-4f81-b627-fa83d9c50f88" />
