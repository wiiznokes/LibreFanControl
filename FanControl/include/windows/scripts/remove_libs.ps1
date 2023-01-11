<#
Usage:
    -jvm :  remove all the files inside JAVA_HOME lib directory witch have
            the same name of any file inside libWindowsJava directory

    -app :  remove all duplicate files between app and jvm folder; files in
            jvm are keep

    -src :  path to the directory witch serve as reference for files names
    -rm :   path to the directory were files have to be removed"
#>


if ($args.Length -lt 1)
{
    Write-Error "No argument spécified, see script for details"
    exit 1
}

# check JAVA_HOME variable
if ($args -contains "-jvm")
{
    if ($null -eq $env:JAVA_HOME)
    {
        Write-Output "The JAVA_HOME environment variable does not exist."
        exit 1
    }
    if (!(Test-Path ($env:JAVA_HOME + "\bin")))
    {
        Write-Output "JAVA_HOME is not a valid directory"
        exit 1
    }
}

# set source dir
if ($args -contains "-src")
{
    $srcDir = $args[($args.IndexOf("-src") + 1)]
}
else
{
    $srcDir = "./../jvm"
}

# set remove dir
if ($args -contains "-rm")
{
    $rmDir = $args[($args.IndexOf("-rm") + 1)]
}
else
{
    if ($args -contains "-jvm")
    {
        $rmDir = $env:JAVA_HOME + "\bin"
    }
    else
    {
        $rmDir = "./../app"
    }
}


$srcFiles = Get-ChildItem $srcDir
$removeFiles = Get-ChildItem $rmDir

foreach ($src in $srcFiles)
{
    # Check if a file with the same name as the current source file exists in the remove directory
    $file = $removeFiles | Where-Object { $_.Name -eq $src.Name }
    if ($file)
    {
        Write-Output "Remove file $file"
        Remove-Item $file.FullName
    }
}