import styled from 'styled-components';

const Section = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export default function OtherPage() {
    return (
        <Section>
            <h1>Hello from the other Side</h1>
        </Section>
    );
}
