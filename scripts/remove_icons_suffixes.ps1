# Ce script renomme tous les fichiers dans le répertoire courant en enlevant le suffixe "_FILL"
Set-Location "./../FanControl/src/jvmMain/resources/drawable"
$files = Get-ChildItem -Recurse
foreach ($file in $files)
{
    # Remplace le suffixe par une chaîne vide
    $newName = $file.Name.Replace('_FILL','')
    Rename-Item -Path $file.FullName -NewName $newName
}