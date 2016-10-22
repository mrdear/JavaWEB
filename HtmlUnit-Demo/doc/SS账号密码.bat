@echo off
color 1f
cls
echo.
echo 1获取账号
echo.
echo 2退出
echo.
SET t=
SET /P t=请选择1/2:
IF /I '%t:~0,1%'=='1' GOTO start
IF /I '%t:~0,1%'=='2' GOTO stop
exit

:start
echo 正在获取,请稍后
java -jar E://jar/mrdear-1.0.jar
start D:\tools\翻墙\ShadowsocksR-dotnet4.0.exe
goto finish

:stop
echo 正在退出,请稍后


goto end
:end
exet