# Calculadora Estadistica XML

Se requiere el desarrollo de una aplicación Java para calcular estadísticas de variables numéricas almacenadas en un documento XML.

La aplicación debe recibir a través de la línea de comandos el nombre de un documento XML y al menos el nombre de una variable.

Para la variable se debe calcular:
- Media
- Desviación estándar
- Varianza
- Valor máximo
- Valor mínimo 

Si el usuario provee el nombre de dos variables, la aplicación debe calcular todas las estadísticas anteriores para cada variable y adicionalmente el coeficiente de correlación entre las dos variables.

La aplicación puede asumir qué el documento XML está bien formado. Sin embargo, debe realizar un manejo efectivo de situaciones excepcionales y errores. Por ejemplo:

- No se puede abrir el documento XML
- El usuario se equivoca en el nombre de la variable o la variable no existe en el documento.
- La variable qué seleccionó el usuario no es una variable numérica.

Debe incluir el uso de al menos una clase del Java Collections API.


## Solucion implementada

### Estructura del codigo

- `Calculadora.java`: clase principal con el metodo main. Valida los
  argumentos de entrada, coordina la lectura del XML, calcula e
  imprime las estadisticas, y maneja los errores.
- `VariablesHandler.java`: manejador SAX que recorre el documento XML
  y extrae los valores numericos de la variable buscada.




### Estadisticas calculadas

Para cada variable:

- Media
- Desviacion estandar
- Varianza
- Valor maximo
- Valor minimo

Si se pasan dos variables, ademas se calcula el coeficiente de
correlacion de Pearson entre ambas.

### Manejo de errores

- Si no se pasan argumentos o solo se pasa uno, se muestra un mensaje
  y se termina la ejecucion.
- Si el archivo XML no existe, se avisa al usuario.
- Si la variable buscada no aparece en el documento, se avisa.
- Si la variable existe pero no es numerica, se lanza una excepcion
  con el valor problematico.

### Uso de Java Collections API

Se utiliza `ArrayList<Double>` en `VariablesHandler` para acumular los
valores leidos del XML antes de calcular las estadisticas.

### Ejemplo de salida
Estadisticas de 'rd_spend':
Media: 73721.6156
Desviacion estandar: 45440.9156
Varianza: 2064876807.1642
Maximo: 165349.2000
Minimo: 0.0000

Estadisticas de 'profit':
Media: 112012.6392
Desviacion estandar: 39901.0828
Varianza: 1592096409.9430
Maximo: 192261.8300
Minimo: 14681.4000

Coeficiente de correlacion entre rd_spend y profit: 0.9729