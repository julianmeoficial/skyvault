import React, { useEffect, useRef, useMemo, useCallback } from 'react';
import { gsap } from 'gsap';
import { useTheme } from '../../../../shared/contexts/ThemeContext';
import styles from './CodeBlock.module.css';

interface CodeBlockProps {
    code: string;
    language?: 'json' | 'javascript' | 'typescript' | 'bash';
}

const CodeBlock: React.FC<CodeBlockProps> = ({ code, language = 'json' }) => {
    const codeRef = useRef<HTMLPreElement>(null);
    const { theme } = useTheme();

    // MOVER highlightSyntax ANTES de useMemo
    const highlightSyntax = useCallback((text: string, lang: string) => {
        if (lang === 'json') {
            // Optimización: Usar una sola regex con grupos de captura
            return text
                .replace(/"([^"]+)":/g, `<span class="${styles.key}">"$1":</span>`)
                .replace(/"([^"]+)"/g, `<span class="${styles.string}">"$1"</span>`)
                .replace(/\b(\d+)\b/g, `<span class="${styles.number}">$1</span>`)
                .replace(/\b(true|false|null)\b/g, `<span class="${styles.boolean}">$1</span>`);
        }
        return text;
    }, []);

    // Memoizar el formateo de código para evitar recalculos
    const formattedLines = useMemo(() => {
        const lines = code.trim().split('\n');
        return lines.map((line, index) => {
            const trimmedLine = line.trimEnd();
            const indent = line.search(/\S/);

            return {
                index,
                indent,
                content: highlightSyntax(trimmedLine.trim(), language)
            };
        });
    }, [code, language, highlightSyntax]);

    // Animación SOLO una vez al montar
    useEffect(() => {
        if (!codeRef.current) return;

        const lines = codeRef.current.querySelectorAll(`.${styles.line}`);

        // Usar gsap.set para setup inicial
        gsap.set(lines, {
            force3D: true,
            backfaceVisibility: 'hidden'
        });

        // Animación de entrada optimizada
        gsap.fromTo(
            lines,
            { opacity: 0, x: -20 },
            {
                opacity: 1,
                x: 0,
                duration: 0.05,
                stagger: 0.02,
                ease: 'power1.out',
                force3D: true
            }
        );
    }, []); // Solo al montar, no cuando cambie code

    const getThemeClass = () => {
        return theme === 'light' ? styles.light : theme === 'minimal' ? styles.minimal : styles.dark;
    };

    return (
        <pre ref={codeRef} className={`${styles.codeBlock} ${getThemeClass()}`}>
            <code className={styles.code}>
                {formattedLines.map(({ index, indent, content }) => (
                    <div key={index} className={styles.line}>
                        <span className={styles.lineNumber}>{index + 1}</span>
                        <span
                            className={styles.lineContent}
                            style={{ paddingLeft: `${indent * 8}px` }}
                            dangerouslySetInnerHTML={{ __html: content }}
                        />
                    </div>
                ))}
            </code>
        </pre>
    );
};

export default React.memo(CodeBlock);
