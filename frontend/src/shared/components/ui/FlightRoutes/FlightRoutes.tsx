import React, { useRef, useEffect } from 'react';
import { gsap } from 'gsap';
import './FlightRoutes.css';

interface Route {
    start: { lat: number; lng: number };
    end: { lat: number; lng: number };
}

const FlightRoutes: React.FC = () => {
    const svgRef = useRef<SVGSVGElement>(null);

    // Rutas de ejemplo (ciudades importantes para aviación comercial)
    const routes: Route[] = [
        // Nueva York - Londres
        { start: { lat: 40.7128, lng: -74.0060 }, end: { lat: 51.5074, lng: -0.1278 } },
        // Los Ángeles - Tokio
        { start: { lat: 34.0522, lng: -118.2437 }, end: { lat: 35.6762, lng: 139.6503 } },
        // Dubai - Sídney
        { start: { lat: 25.2048, lng: 55.2708 }, end: { lat: -33.8688, lng: 151.2093 } },
        // París - Singapur
        { start: { lat: 48.8566, lng: 2.3522 }, end: { lat: 1.3521, lng: 103.8198 } },
        // Frankfurt - São Paulo
        { start: { lat: 50.1109, lng: 8.6821 }, end: { lat: -23.5505, lng: -46.6333 } },
        // Estambul - Bogotá (IST - BOG)
        { start: { lat: 41.2753, lng: 28.7519 }, end: { lat: 4.7110, lng: -74.0721 } },
        // Estambul - Seúl Incheon (IST - ICN)
        { start: { lat: 41.2753, lng: 28.7519 }, end: { lat: 37.4602, lng: 126.4407 } },
    ];

    const projectPoint = (lat: number, lng: number) => {
        const x = (lng + 180) * (800 / 360);
        const y = (90 - lat) * (400 / 180);
        return { x, y };
    };

    const createCurvedPath = (
        start: { x: number; y: number },
        end: { x: number; y: number }
    ) => {
        const midX = (start.x + end.x) / 2;
        const midY = Math.min(start.y, end.y) - 50;
        return `M ${start.x} ${start.y} Q ${midX} ${midY} ${end.x} ${end.y}`;
    };

    useEffect(() => {
        if (!svgRef.current) return;

        const paths = svgRef.current.querySelectorAll('.flight-path');

        paths.forEach((path, i) => {
            gsap.fromTo(
                path,
                { strokeDashoffset: 1000, strokeDasharray: 1000 },
                {
                    strokeDashoffset: 0,
                    duration: 2,
                    delay: i * 0.3,
                    ease: 'power2.out',
                    repeat: -1,
                    repeatDelay: 5,
                }
            );
        });
    }, []);

    return (
        <div className="flight-routes">
            {/* World Map Background (Simplified dots) */}
            <svg className="flight-routes__map" viewBox="0 0 800 400">
                <defs>
                    <pattern id="dots" x="0" y="0" width="10" height="10" patternUnits="userSpaceOnUse">
                        <circle cx="5" cy="5" r="0.8" fill="rgba(255, 255, 255, 0.15)" />
                    </pattern>
                </defs>
                <rect width="800" height="400" fill="url(#dots)" />
            </svg>

            {/* Flight Routes SVG */}
            <svg
                ref={svgRef}
                className="flight-routes__svg"
                viewBox="0 0 800 400"
            >
                <defs>
                    <linearGradient id="path-gradient" x1="0%" y1="0%" x2="100%" y2="0%">
                        <stop offset="0%" stopColor="white" stopOpacity="0" />
                        <stop offset="5%" stopColor="#B2E5FF" stopOpacity="1" />
                        <stop offset="95%" stopColor="#B2E5FF" stopOpacity="1" />
                        <stop offset="100%" stopColor="white" stopOpacity="0" />
                    </linearGradient>
                </defs>

                {routes.map((route, i) => {
                    const startPoint = projectPoint(route.start.lat, route.start.lng);
                    const endPoint = projectPoint(route.end.lat, route.end.lng);
                    const pathD = createCurvedPath(startPoint, endPoint);

                    return (
                        <g key={`route-${i}`}>
                            {/* Route Path */}
                            <path
                                className="flight-path"
                                d={pathD}
                                fill="none"
                                stroke="url(#path-gradient)"
                                strokeWidth="1.5"
                            />

                            {/* Start Point */}
                            <g className="flight-point">
                                <circle
                                    cx={startPoint.x}
                                    cy={startPoint.y}
                                    r="3"
                                    fill="#B2E5FF"
                                />
                                <circle
                                    className="flight-pulse"
                                    cx={startPoint.x}
                                    cy={startPoint.y}
                                    r="3"
                                    fill="#B2E5FF"
                                    opacity="0.6"
                                />
                            </g>

                            {/* End Point */}
                            <g className="flight-point">
                                <circle
                                    cx={endPoint.x}
                                    cy={endPoint.y}
                                    r="3"
                                    fill="#B2E5FF"
                                />
                                <circle
                                    className="flight-pulse"
                                    cx={endPoint.x}
                                    cy={endPoint.y}
                                    r="3"
                                    fill="#B2E5FF"
                                    opacity="0.6"
                                />
                            </g>
                        </g>
                    );
                })}
            </svg>
        </div>
    );
};

export default FlightRoutes;
