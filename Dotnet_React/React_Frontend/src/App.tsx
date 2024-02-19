import { useEffect, useState } from "react";
import { Tab, initMDB } from "mdb-ui-kit";
import { fetchNames, fetchDetails } from "./components/PersonApi";
import PersonList from "./components/PersonList";
import PersonDetails from "./components/PersonDetails";

function App() {
  const [personNames, setPersonNames] = useState<string[]>([]);
  const [selectedPersonName, setSelectedPersonName] = useState<
    string | undefined
  >(undefined);
  const [selectedPersonDetails, setSelectedPersonDetails] = useState<any>();

  const baseUrl = "https://localhost:7017";
  console.log("Base URL:", baseUrl);

  initMDB({ Tab });

  useEffect(() => {
    fetchNames(baseUrl, (x) => setPersonNames(x));
  }, []);

  useEffect(() => {
    if (selectedPersonName == undefined) {
      console.log("Selected name: undefined");
      setSelectedPersonDetails(undefined);
      return;
    }
    fetchDetails(baseUrl, selectedPersonName!, (x) => {
      console.log("Selected name: ", x);
      setSelectedPersonDetails(x);
    });
  }, [selectedPersonName]);

  console.log("Rendering App");

  return (
    <>
      <h1 className="m-3">Hackathon .NET / React demo and template</h1>
      <ul className="nav nav-tabs mb-3" id="t_nav" role="tablist">
        <li className="nav-item" role="presentation">
          <a
            data-mdb-tab-init
            className="nav-link active"
            id="tab_persons_nav"
            href="#tab_persons_content"
            role="tab"
            aria-controls="tab_persons_content"
            aria-selected="true"
          >
            <button className="btn">Persons</button>
          </a>
        </li>
        <li className="nav-item" role="presentation">
          <a
            data-mdb-tab-init
            className="nav-link"
            id="tab_graph_nav"
            href="#tab_graph_content"
            role="tab"
            aria-controls="tab_graph_content"
            aria-selected="false"
          >
            <button className="btn">Graph</button>
          </a>
        </li>
        <li className="nav-item" role="presentation">
          <a
            data-mdb-tab-init
            className="nav-link"
            id="tab_departure_nav"
            href="#tab_departure_content"
            role="tab"
            aria-controls="tab_departure_content"
            aria-selected="false"
          >
            <button className="btn">Departures</button>
          </a>
        </li>
      </ul>
      <div className="tab-content" id="t_content">
        <div
          className="tab-pane fade show active"
          id="tab_persons_content"
          role="tabpanel"
          aria-labelledby="tab_persons_content"
        >
          <div className="grid p-0 mx-3">
            <div className="row">
              <div className="col-2 p-0">
                <PersonList
                  names={personNames}
                  onNameSelected={setSelectedPersonName}
                />
              </div>
              <div className="col p-3 ms-3 border rounded">
                <PersonDetails
                  details={selectedPersonDetails}
                  imageSize={100}
                />
              </div>
            </div>
          </div>
        </div>
        <div
          className="tab-pane fade"
          id="tab_graph_content"
          role="tabpanel"
          aria-labelledby="tab_graph_content"
        >
          Graph
        </div>
        <div
          className="tab-pane fade"
          id="tab_departure_content"
          role="tabpanel"
          aria-labelledby="tab_departure_content"
        >
          Departures
        </div>
      </div>
    </>
  );
}

export default App;
