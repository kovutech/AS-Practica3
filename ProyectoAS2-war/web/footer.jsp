<%-- 
    Document   : footer
    Created on : 04-jun-2018, 19:03:16
    Author     : Jorge
--%>

<div class='footer'>
    <table border='0' class="footerTable" align="center">
        <tr>
            <th>Asignatura</th>
            <th>Alumno</th>
            <td rowspan="2">
                <FORM action='FrontController'>
                    <INPUT type='hidden' name='command' value='Stadistics'>
                    <INPUT type='submit' value='Estadisticas' class='boton'>
                </FORM>
            </td>
            <td rowspan="2">
                <FORM action='FrontController'>
                    <INPUT type='hidden' name='command' value='Log'>
                    <INPUT type='submit' value='Log' class='boton'>
                </FORM>
            </td>
        </tr>
        <tr>
            <td>Arquitectura del Software</td>
            <td>Jorge Fernández Molines</td>
        </tr>
    </table>
</div>
