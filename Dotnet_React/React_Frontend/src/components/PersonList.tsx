import Async from "react-async";
import { useState } from "react";

interface PersonListProps {
  baseUrl: string;
  onSelectItem: (item: string) => void;
}

function PersonList({ baseUrl, onSelectItem }: PersonListProps) {
  const [selectedIndex, setSelectedIndex] = useState(-1);

  const content = async () => {
    const res = await fetch(baseUrl + "/persons/names");
    const data = await res.json();
    const names: string[] = data;
    console.log(names);
    return names;
  };

  return (
    <Async promiseFn={content} def={""}>
      <Async.Pending>{<p>Loading</p>}</Async.Pending>
      <Async.Fulfilled>
        {(data: string[]) => (
          <ul className="list-group">
            {data.map((item, index) => (
              <li
                className={
                  selectedIndex === index
                    ? "list-group-item active"
                    : "list-group-item"
                }
                key={item}
                onClick={() => {
                  setSelectedIndex(index);
                  onSelectItem(item);
                }}
              >
                {item}
              </li>
            ))}
          </ul>
        )}
      </Async.Fulfilled>
      <Async.Rejected>
        {(error) => `Something went wrong: ${error.message}`}
      </Async.Rejected>
    </Async>
  );
}

export default PersonList;
