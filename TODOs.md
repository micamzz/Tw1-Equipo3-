# TODOs

## Goal
Build a fantasy basketball app where the user starts with a fixed budget, selects a 10-player roster, and earns points based on the real performance of the players in the tournament.

Current important pieces in the codebase:
- `src/main/java/com/tallerwebi/dominio/Jugador.java`
- `src/main/java/com/tallerwebi/dominio/Equipo.java`
- `src/main/java/com/tallerwebi/dominio/Liga.java`
- `src/main/java/com/tallerwebi/dominio/SimuladorDePartidos.java`
- `src/main/java/com/tallerwebi/dominio/GeneradorDeEquipos.java`
- `src/main/java/com/tallerwebi/dominio/EstadisticaDePartidoDelJugador.java`
- `src/main/java/com/tallerwebi/presentacion/ControladorLogin.java`
- `src/main/webapp/WEB-INF/views/thymeleaf/home.html`
- `src/main/webapp/WEB-INF/views/thymeleaf/login.html`

## Step-by-step implementation plan

### 1. Define the fantasy domain model
- Create a new class to represent the user's fantasy roster.
- Store:
  - the team owner or user
  - the remaining budget
  - the selected 10 players
  - the role assigned to each selected player in the fantasy team
- Decide whether each fantasy player references a real `Jugador` or stores just the player ID/name.
- Add validation so the roster always has exactly:
  - 5 starters
  - 1 sixth man
  - 4 substitutes

### 2. Define budget rules
- Add a fixed starting budget constant.
- Decide how a player's cost is calculated.
- Use a predictable pricing rule based on something already available, such as:
  - `Jugador.getValorDeMercado()`
  - or a new price field if you want prices to be independent from stats
- Add checks so the user cannot exceed the budget.
- Add helper methods to:
  - calculate roster total cost
  - calculate remaining budget
  - reject invalid selections

### 3. Expose the tournament players to the fantasy flow
- Reuse the players created by `GeneradorDeEquipos`.
- Add a service or helper that returns all available players from all teams.
- Make sure the fantasy selection screen can show every player in the tournament.
- If needed, add filters by:
  - position
  - role
  - team
  - value

### 4. Build the roster selection service
- Create a service that receives selected players and validates the fantasy roster.
- Validate:
  - no duplicate player selection
  - the total number of players is 10
  - the role distribution is correct
  - the budget is enough
- Return clear errors when a selection is invalid.
- Keep this logic in the domain/service layer, not in the controller.

### 5. Connect fantasy points to match simulation
- Decide how fantasy points are calculated.
- A simple first version can reuse the statistics already generated in `SimuladorDePartidos`.
- Create a method that sums points for the players in the fantasy roster.
- Use only the players selected by the user, not all tournament players.
- Define whether the points are:
  - only points scored
  - or a weighted score using rebounds, assists, steals, blocks, and turnovers

### 6. Decide how matchdays update fantasy scoring
- Make sure each simulated `fecha` updates the fantasy team.
- Keep track of which real players contributed in each round.
- Add a method to calculate the fantasy score for a single date.
- Add another method to calculate the cumulative score for the full tournament.

### 7. Generate the tournament fixture correctly
- Use the round-robin logic in `Liga`.
- Verify that every pair of teams plays twice:
  - once with one team as local
  - once with the same matchup reversed
- Make sure a team does not play against itself.
- Confirm that odd numbers of teams are handled cleanly.

### 8. Add persistence if the app must remember the user's team
- Check whether the fantasy roster should survive page reloads.
- If yes, save it in the database.
- Add the corresponding repository and entity mapping.
- Persist:
  - selected players
  - budget spent
  - remaining budget
  - accumulated points

### 9. Add presentation screens
- Create a page to build the fantasy roster.
- Create a page to show:
  - budget remaining
  - selected players
  - roster validation errors
  - current fantasy points
- Add a results page to show the fantasy score after each round.
- Keep the user flow simple:
  - login
  - go to fantasy team creation
  - select players
  - save roster
  - view points

### 10. Wire the controller layer
- Add a controller dedicated to fantasy team management.
- Add routes for:
  - viewing available players
  - adding/removing players
  - saving the roster
  - viewing points
- Keep the controller thin.
- Push validation and scoring logic into services.

### 11. Add tests before moving on
- Write unit tests for:
  - budget validation
  - roster size validation
  - duplicate player rejection
  - fantasy score calculation
- Write tests for the round-robin fixture if you touch `Liga` again.
- Add at least one integration test for the fantasy roster flow.

### 12. Polish the UI
- Improve the fantasy roster page so the user can understand:
  - how much budget is left
  - how many slots are still open
  - which positions are missing
- Highlight invalid selections clearly.
- Show the current total score and a round-by-round summary.

## Suggested implementation order
1. Create the fantasy roster domain model.
2. Add budget and validation rules.
3. Reuse existing players from the tournament.
4. Add fantasy score calculation.
5. Wire controller and views.
6. Add persistence only if needed.
7. Write tests.
8. Polish the UI.

## Definition of done
- The user can create a fantasy roster with exactly 10 players.
- The roster respects the 5 + 1 + 4 structure.
- The roster cannot exceed the fixed budget.
- The roster only uses players from the tournament.
- The app awards points from real simulated player performance.
- The user can see current and accumulated fantasy points.
- The flow is covered by tests.

## Notes
- Keep the simulation logic separate from the fantasy roster logic.
- Avoid putting business rules in the controller.
- Prefer small services and clear domain objects.
- If a rule is still unclear, write the code so it is easy to adjust later.
