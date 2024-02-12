import PersonList from "./components/PersonList";
import PersonDetails from "./components/PersonDetails";

function App() {
  const handlePersonSelection = (person: string) => {
    console.log(person);
  };

  return (
    <div className="container text-center">
      <div className="row">
        <PersonList
          baseUrl="https://localhost:7017"
          onSelectItem={handlePersonSelection}
        />
        <div className="col">
          <PersonDetails baseUrl="https://localhost:7017" name="Megumin" />
        </div>
      </div>
    </div>
  );
}

export default App;
