# ADR-008 — Engine Gráfica 2D

- **Status:** Aprovada

## Decisão
Utilizar PixiJS para renderização 2D, integrado ao frontend React.

## Diretrizes
- React controla a interface da aplicação.
- PixiJS renderiza o mundo/cena 2D.
- O modelo de cena é independente da engine gráfica.
- A camada de interação é própria e separada da renderização.
- O domínio e o backend não dependem de PixiJS.
- A abstração deve permitir avaliar outra engine, inclusive 3D, no futuro.

## Requisitos arquiteturais
A solução deve considerar mapas grandes e grande quantidade de tokens. Coordenadas de mundo não podem depender diretamente de pixels, zoom ou dimensões da tela.
