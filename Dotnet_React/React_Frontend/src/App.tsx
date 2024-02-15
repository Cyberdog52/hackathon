import { useEffect, useState } from "react";
import {fetchNames, fetchDetails} from "./components/PersonApi";
import PersonList from "./components/PersonList";
import PersonDetails from "./components/PersonDetails";

function App() {
  const [personNames, setPersonNames] = useState<string[]>([]);
  const [selectedPersonName, setSelectedPersonName] = useState<string>("");
  const [selectedPersonDetails, setSelectedPersonDetails] = useState<any>();

  const baseUrl = "https://localhost:7017";
  console.log("Base URL:", baseUrl)

  useEffect(() => {
    fetchNames(baseUrl, (x) => setPersonNames(x));
  }, []);

  useEffect(() =>{
    fetchDetails(baseUrl, selectedPersonName, (x) => setSelectedPersonDetails(x));
  }, [selectedPersonName]);

  console.log("Rendering App")

  return (
    <div className="text-align-center">
      <h1 className="m-3">Hackathon .NET / React demo and template</h1>
      <div className="container text-left m-3">
        <div className="row">
          <div className="col-4 m-3">
            <PersonList
              names={personNames}
              onNameSelected={setSelectedPersonName}
            />
          </div>
          <div className="col-6 m-3 border rounded">
            <PersonDetails details={selectedPersonDetails} />
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
