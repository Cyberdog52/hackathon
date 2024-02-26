import { useState } from "react";

interface DiagramViewProps {
  names: string[];
  onDiagramSelect: (name: string) => any;
}

export default function DiagramView({
  names,
  onDiagramSelect,
}: DiagramViewProps) {
  const [selectedIndex, setSelectedIndex] = useState(-1);
  const [selectedName, setSelectedName] = useState("");

  return (
    <>
      <div className="dropdown">
        <a
          className="btn btn-secondary dropdown-toggle"
          href="#"
          role="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          Dropdown link
        </a>
        <ul className="dropdown-menu">
          {/* {names.map((item, index) => (
              <li>
                <a className="dropdown-item" href="#" key={item} />
                {item}
                <a/>
              </li>
            ))} */}
          <li>
            <a className="dropdown-item" href="#" key="xxx">
            xxx
            </a>
          </li>
        </ul>
      </div>
      <div></div>
    </>
  );
}
