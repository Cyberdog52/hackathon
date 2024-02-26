import { useEffect, useState } from "react";
import { Tab, initMDB } from "mdb-ui-kit";
import { fetchPersonNames, fetchPersonDetails } from "./components/PersonApi";
import { fetchDiagramNames, fetchDiagram } from "./components/DiagramApi";
import PersonList from "./components/PersonList";
import PersonDetails from "./components/PersonDetails";
import DiagramView from "./components/DiagramView";

function App() {
  const [personNames, setPersonNames] = useState<string[]>([]);
  const [selectedPersonName, setSelectedPersonName] = useState<
    string | undefined
  >(undefined);
  const [selectedPersonDetails, setSelectedPersonDetails] = useState<any>();
  const [diagramNames, setDiagramNames] = useState<string[]>([]);

  const apiUrl = "https://localhost:7017";

  initMDB({ Tab });

  useEffect(() => {
    fetchPersonNames(apiUrl, (x) => setPersonNames(x));
  }, []);

  useEffect(() => {
    if (selectedPersonName == undefined) {
      console.log("Selected person name: undefined");
      setSelectedPersonDetails(undefined);
      return;
    }
    fetchPersonDetails(apiUrl, selectedPersonName!, (x) => {
      console.log("Selected person name: ", x);
      setSelectedPersonDetails(x);
    });
  }, [selectedPersonName]);

  useEffect(() => {
    fetchDiagramNames(apiUrl, (x) => setDiagramNames(x));
  }, []);

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
            <button className="btn">Diagrams</button>
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
            <button className="btn">Jokes</button>
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
          <DiagramView
            names={diagramNames}
            onDiagramSelect={(name) =>
              fetchDiagram(apiUrl, name, (x) => {
                console.log("Selected diagram name: ", x);
                return x;
              })
            }
          />
        </div>
        <div
          className="tab-pane fade"
          id="tab_departure_content"
          role="tabpanel"
          aria-labelledby="tab_departure_content"
        >
          Jokes
        </div>
      </div>
    </>
  );
}

export default App;
