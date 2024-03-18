import styled from 'styled-components';
import Button from '../shared/Button.tsx';
import remoteService from '../services/RemoteService.tsx';
import { useEffect, useState } from 'react';
import LoadingPage from '../shared/LoadingPage.tsx';
import { ExampleDto } from '../shared/model.tsx';

const Section = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ExampleTitle = styled.h3`
  font-size: 4rem;
  margin-bottom: 0;
  color: var(--secondary);
  padding-bottom: 4%;
`;

export default function MainPage() {
    function handleClick() {
        loadExample();
    }

    const [example, setExample] = useState<ExampleDto | undefined>(undefined);

    useEffect(() => {
        loadExample();
    }, []);

    function loadExample() {
        setExample(undefined);
        remoteService.get<ExampleDto>('/example/').then((response: ExampleDto) => {
            console.log(response);
            setExample(response);
        });
    }

    if (example == undefined) {
        return <LoadingPage/>;
    }

    return (
        <Section>
            <ExampleTitle>{example.name} {example.value}</ExampleTitle>
            <Button onClick={handleClick}>Refresh</Button>
        </Section>
    );
}
