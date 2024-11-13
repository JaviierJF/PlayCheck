CREATE TABLE Usuario (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    fecha_registro DATE NOT NULL,
    avatar_url VARCHAR(255),
    rol VARCHAR(100)
);

CREATE TABLE Videojuego (
    juego_id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    desarrollador VARCHAR(100),
    genero VARCHAR(50),
    fecha_lanzamiento DATE,
    descripcion TEXT
);

CREATE TABLE Resena (
    resena_id INT PRIMARY KEY AUTO_INCREMENT,
    contenido TEXT NOT NULL,
    fecha DATE NOT NULL,
    user_id INT,
    juego_id INT,
    FOREIGN KEY (user_id) REFERENCES Usuario(user_id) ON DELETE CASCADE,
    FOREIGN KEY (juego_id) REFERENCES Videojuego(juego_id) ON DELETE CASCADE
);

CREATE TABLE Valoracion (
    valoracion_id INT PRIMARY KEY AUTO_INCREMENT,
    puntuacion TINYINT NOT NULL CHECK (puntuacion BETWEEN 1 AND 10),
    fecha DATE NOT NULL,
    user_id INT,
    juego_id INT,
    FOREIGN KEY (user_id) REFERENCES Usuario(user_id) ON DELETE CASCADE,
    FOREIGN KEY (juego_id) REFERENCES Videojuego(juego_id) ON DELETE CASCADE
);

CREATE TABLE Lista_Juegos (
    lista_id INT PRIMARY KEY AUTO_INCREMENT,
    estado ENUM('Jugando', 'Completado', 'Pendiente') NOT NULL,
    user_id INT,
    juego_id INT,
    FOREIGN KEY (user_id) REFERENCES Usuario(user_id) ON DELETE CASCADE,
    FOREIGN KEY (juego_id) REFERENCES Videojuego(juego_id) ON DELETE CASCADE
);


CREATE TABLE Amigos (
    amigo_id INT PRIMARY KEY AUTO_INCREMENT,
    user1_id INT NOT NULL,
    user2_id INT NOT NULL,
    fecha_amistad DATE NOT NULL,
    FOREIGN KEY (user1_id) REFERENCES Usuario(user_id) ON DELETE CASCADE,
    FOREIGN KEY (user2_id) REFERENCES Usuario(user_id) ON DELETE CASCADE,
    CONSTRAINT unique_amigos_pair UNIQUE (user1_id, user2_id)
);

CREATE TABLE Comentarios (
    comentario_id INT PRIMARY KEY AUTO_INCREMENT,
    contenido TEXT NOT NULL,
    fecha DATE NOT NULL,
    resena_id INT,
    user_id INT,
    FOREIGN KEY (resena_id) REFERENCES Resena(resena_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Usuario(user_id) ON DELETE CASCADE
);


CREATE TABLE Genero (
    genero_id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Videojuego_Genero (
    juego_id INT,
    genero_id INT, 
    FOREIGN KEY (juego_id) REFERENCES Videojuego(juego_id) ON DELETE CASCADE,
    FOREIGN KEY (genero_id) REFERENCES Genero(genero_id) ON DELETE CASCADE,
    PRIMARY KEY (juego_id, genero_id)
);

CREATE TABLE ValoracionWeb (
    valoracionWeb_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    puntuacion INT CHECK (puntuacion BETWEEN 1 AND 5),
    comentario TEXT,
    fecha_valoracion DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Usuario(user_id) ON DELETE CASCADE
);
