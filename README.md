# Proyecto-Juego-Progra1

## Integrantes.
1. Diego Camacho Martinez. C4D598
1. Gabriel(Gabo) Coto Fernandez. C4E540
1. John Esteban Vargas Arroyo. C4K672

# ¿Qué es el proyecto?

El proyecto consiste en un juego roguelike básico donde el jugador puede moverse por una habitación generada aleatoriamente. En esta habitación se encuentran monstruos, ítems y una puerta. El objetivo es escapar de la mazmorra eliminando la mayor cantidad posible de enemigos y obteniendo ítems para mejorar las características del jugador. Al morir, el juego se reinicia desde cero.

# ¿Cómo jugar?

Al jugador se le presenta una matriz 9x9 donde:
- '#' representa las paredes
- '/' es la puerta de salida
- 'E' representa los enemigos
- '+' representa los ítems
- '@' representa al jugador

El movimiento se realiza utilizando las teclas 'w', 'a', 's' y 'd'. Al recoger un ítem, se notifica al jugador qué objeto obtuvo. Estos ítems pueden utilizarse durante el combate con los enemigos, los cuales pueden detectar al jugador a un espacio de distancia (atacan en espacios adyacentes a su posición).

Durante el combate se muestra:
- La vida del jugador
- La vida del enemigo

El jugador puede elegir entre:
- Atacar (opción '0')
- Usar un ítem (opción '1')

Al usar un ítem, el jugador debe seleccionar uno de su inventario utilizando los índices '0', '1' o '2'. Si el ítem tiene un efecto aplicable, se utilizará; de lo contrario, se mostrará un aviso de ítem inválido. Esto puede ocurrir por seleccionar un índice incorrecto. El enemigo atacará al jugador en cada turno, incluso cuando este utilice un ítem.

# Implementaciones futuras

Se planea:
1. Crear una base de ítems más extensa que incluya:
   - Efectos fuera de combate
   - Efectos diferentes a solo mejorar ataque o defensa
2. Generar más habitaciones
3. Implementar una condición de victoria
4. Añadir un sistema de dificultad progresiva que:
   - Aumente con cada nivel completado
   - Afecte a los ítems y monstruos disponibles
   