# Groupe de blanch_b maro_j

Pour lancer notre application, voici les instructions : 

### Base de donnée

````
docker compose up
````

### Spring Boot

````
cd SocialShelf_API
./mvnw spring-boot:run
````
port : 8090

### React

````
cd socialShelf_Front
npm install
npm start
````
port : 3000



Profitez !




#### NB : CORS Demonstration
Nous avons sécuriser Spring Boot afin de n'accepter des requêtes venant uniquement du localhost sur le port 3000.
Pour le tester, vous pouvez lancer l'application React avec : 
Linux/MacOS -> `npm changeport_linux`
Windows -> `npm run changeport_windows`
Vous pourrez voir que l'application n'a pas les autorisations de Cross Origin à cause du changement de port 

