# Ce script renomme tous les fichiers dans le répertoire courant en enlevant le suffixe "_FILL"
Set-Location "./../FanControl/src/jvmMain/resources/drawable"
$files = Get-ChildItem -Recurse
foreach ($file in $files)
{
    $parts = $file.Name.Split('_FILL')
    if ($parts.Count -eq 2)
    {
        Write-Output "$parts"
        $newName = $parts[0].Insert($parts[0].Length, '.svg')
        Rename-Item -Path $file.FullName -NewName $newName
    }
}


Set-Location $PSScriptRoot