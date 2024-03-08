import styled from 'styled-components';
import { ReactNode } from 'react';

const StyledButton = styled.button`
  background-color: var(--secondary);
  font-weight: bold;
  color: white;
  padding: 1rem 2rem;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  cursor: pointer;
  border-radius: 4px;
  border: 1px solid var(--secondary);

  &:hover {
    color: var(--secondary);
    background-color: white;
  }

  @media (max-width: 500px) {
    padding: 1rem;
  }
`;

interface Props {
    children: ReactNode;
    onClick: () => void;
}

export default function Button({ children, onClick }: Props) {
    return (
        <StyledButton onClick={onClick}>
            {children}
        </StyledButton>
    );
}
