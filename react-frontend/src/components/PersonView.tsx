import { useEffect, useState } from 'react';
import { fetchPersonDetails, fetchPersonNames } from './PersonApi.tsx';
import PersonDetails from './PersonDetails.tsx';

interface PersonViewProps {
    apiUrl: string;
    viewWidth?: number;
    viewHeight?: number;
    viewMargin?: number;
    contentPadding?: number;
}

export default function PersonView({
                                       apiUrl,
                                       viewWidth,
                                       viewHeight,
                                       viewMargin,
                                       contentPadding,
                                   }: PersonViewProps) {
    const [personNames, setPersonNames] = useState<string[]>([]);
    const [selectedPersonName, setSelectedPersonName] = useState<string>('');
    const [selectedPersonDetails, setSelectedPersonDetails] = useState<any>([]);

    useEffect(() => {
        fetchPersonNames(apiUrl, (x) => {
            console.log('Avalable person names: ', x);
            setPersonNames(x);
        });
    }, []);

    useEffect(() => {
        fetchPersonDetails(apiUrl, selectedPersonName!, (x) => {
            console.log('Selected person details: ', x);
            setSelectedPersonDetails(x);
        });
    }, [selectedPersonName]);

    return (
        <div className="grid" style={{ margin: viewMargin }}>
            <div className="row">
                <div
                    className="col-2 btn-group-vertical align-self-start"
                    role="group"
                >
                    {personNames.map((item) => (
                        <>
                            <input
                                type="radio"
                                className="btn-check"
                                name="person_selection"
                                id={item}
                            />
                            <label
                                className="btn btn-outline-primary text-start"
                                htmlFor={item}
                                id={item}
                                onClick={() => {
                                    console.log('Selected person name:', item);
                                    setSelectedPersonName(item);
                                }}
                            >
                                {item}
                            </label>
                        </>
                    ))}
                </div>
                <div className="col-5" style={{ marginLeft: contentPadding }}>
                    <PersonDetails
                        details={selectedPersonDetails}
                        imageWidth={viewWidth}
                        imageHeight={viewHeight}
                        contentPadding={contentPadding}
                    />
                </div>
            </div>
        </div>
    );
}
