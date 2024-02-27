import { useEffect, useState } from "react";
import mermaid from "mermaid";
import { fetchDiagramNames, fetchDiagram } from "./DiagramApi";

interface DiagramViewProps {
  apiUrl: string;
}

export default function DiagramView({
  apiUrl
}: DiagramViewProps) {
  const [diagramNames, setDiagramNames] = useState<string[]>([]);
  const [selectedName, setSelectedName] = useState<string>("");
  const [selectedDiagram, setSelectedDiagram] = useState<any>([]);
  const [diagramSvg, setDiagramSvg] = useState<string>("");

  

  useEffect(() => {
    fetchDiagramNames(apiUrl, (x) => setDiagramNames(x));
  }, []);
  
  useEffect(() => {
    fetchDiagram(apiUrl, selectedName, (x) => setSelectedDiagram(x));
  }, [selectedName]);

  useEffect(() => {
    mermaid.render("mermaid", selectedDiagram.script).then(x => {
      setDiagramSvg(btoa(x.svg));
      console.log(x);
    });
    console.log("Diagram rendered")
  }, [selectedDiagram]);

  return (
    <>
      <div
        className="btn-group-vertical m-3"
        role="group"
        aria-label="Vertical radio toggle button group"
      >
        {diagramNames.map((item) => (
          <>
            <input
              type="radio"
              className="btn-check"
              name="vbtn-radio"
              id={item}
            />
            <label
              className="btn btn-outline-danger p-1"
              htmlFor={item}
              id={item}
              onClick={() => {
                setSelectedName(item);
                console.log("Selected diagram name:", item);
              }}
            >
              {item}
            </label>
          </>
        ))}
      </div>
      <img src={"data:image/svg+xml;base64," + diagramSvg}></img>
      <div id="mermaid"></div>
    </>
  );
}