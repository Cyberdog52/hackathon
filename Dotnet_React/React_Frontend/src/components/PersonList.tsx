import { useState } from "react";

interface PersonListProps {
  names: string[];
  onNameSelected: (item: string) => void;
}

function PersonList({ names, onNameSelected }: PersonListProps) {
  const [selectedIndex, setSelectedIndex] = useState(-1);

  if (names.length === 0) {
    return <p></p>;
  }

  return (
    <ul className="list-group">
      {names.map((item, index) => (
        <li
          className={
            selectedIndex === index
              ? "list-group-item active"
              : "list-group-item"
          }
          key={item}
          onClick={(e) => {
            console.log("Person name change value:", item);
            console.log("Person name change index:", index);
            setSelectedIndex(index);
            onNameSelected(item);
          }}
        >
          {item}
        </li>
      ))}
    </ul>
  );
}

export default PersonList;
