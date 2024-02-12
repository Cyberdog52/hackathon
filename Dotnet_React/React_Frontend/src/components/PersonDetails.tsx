import Async from "react-async";
import { useState } from "react";

interface PersonDetailsProps {
  baseUrl: string;
  name: string;
}

function PersonDetails({ baseUrl, name }: PersonDetailsProps) {
  const content = async () => {
    const res = await fetch(baseUrl + "/" + name + "/details/");
    const data = await res.json();
    console.log(data);
    return data;
  };

  return (
    <Async promiseFn={content} def={null}>
      <Async.Pending>{<p>Loading</p>}</Async.Pending>
      <Async.Fulfilled>
        {(data: any) => (
          <>
            <p>{data.name}</p>
            <p>{data.age}</p>
            <p>{data.role}</p>
            <p>{data.bioUrl}</p>
          </>
        )} 
      </Async.Fulfilled>
      <Async.Rejected>
        {(error) => `Something went wrong: ${error.message}`}
      </Async.Rejected>
    </Async>
  );
}

export default PersonDetails;