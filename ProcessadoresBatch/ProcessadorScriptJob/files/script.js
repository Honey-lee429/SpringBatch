var email = item.getEmail();
print(email);
var filePath = "files/" + email + ".txt";
var path = java.nio.file.Paths.get(filePath);
var arquivoExiste = java.nio.file.Files.exists(path);

if (!arquivoExiste) item; else null;