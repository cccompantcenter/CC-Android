# DS-001 — Command Center Design System

## 1. Design philosophy

Command Center is designed as a calm, premium, tablet-first workspace for focused thinking and action. The experience should feel grounded, deliberate, and trustworthy.

Core principles:
- Calm: quiet surfaces, generous spacing, restrained motion, and clear hierarchy.
- Premium: refined materials, subtle depth, and confident typography.
- Tablet-first: optimized for landscape tablet use, especially Samsung Galaxy Tab S10 Ultra.
- S Pen-first: handwriting is a first-class input method, not a secondary feature.
- AI as assistant: AI should support thinking and organization, never dominate the experience.
- “Rust in je hoofd. Regie in je werk.”: the system should help users think clearly while keeping control over their work.

## 2. Color system

### Core palette
- Primary gold: #D6B56D
- Background black: #11100E
- Surface black: #15130F
- Surface dark: #211E18
- Surface elevated: #2A241D
- Text primary: #F4EFE6
- Text secondary: #E7DFC9
- Muted text: #9E978C

### Accent colors
- Accent gold: #C8A75D
- Accent warm: #B68A3A
- Accent bronze: #8A6A2A

### Status colors
- Success: #5D8C63
- Warning: #C89B3C
- Error: #B85C4A
- Info: #5C7FA8

### AI colors
- AI assist: #6B7F8F
- AI highlight: #A7B8C9
- AI background: #18222B

### Usage guidance
- Use gold for primary actions, emphasis, and premium highlights.
- Use black and dark brown surfaces for the main canvas.
- Keep text contrast high and use gold sparingly for focus.
- Use status colors only for clear system feedback.
- Reserve AI colors for AI-generated suggestions or assistant states.

## 3. Typography

### Type scale
- Page title: 38sp, Light
- Section header: 24sp, Medium
- Card title: 20sp, Light
- Body text: 16sp, Regular
- Metadata: 13sp, Regular
- Handwriting rendering: dynamic, with generous spacing and no forced scaling

### Typography principles
- Use a quiet, editorial tone rather than a playful one.
- Prioritize readability on large tablet screens.
- Keep weight contrast subtle and intentional.
- Handwriting should be allowed to breathe and remain visually authentic.

### Recommended text roles
- Page titles: used only for main screen identity.
- Section headers: used for grouping and structure.
- Card titles: used for concise thought or task labels.
- Body text: used for descriptions and supporting content.
- Metadata: used for time, status, and lightweight context.

## 4. Layout system

### Tablet landscape foundation
- Primary target: Samsung Galaxy Tab S10 Ultra landscape.
- Layout should feel centered, open, and calm.
- Content should not feel crowded even with dense information.

### Grid and spacing
- Base spacing unit: 8dp
- Section spacing: 24dp
- Card padding: 20dp to 24dp
- Screen padding: 24dp to 32dp
- Vertical rhythm: 8dp to 16dp between related elements

### Content width
- Maximum content width: 900dp
- Use centered composition for reading-heavy content.
- Use generous margins to preserve visual calm.

### Card sizing
- Standard card width: full-width within content column
- Card corner radius: 24dp
- Card border: 1dp, low-opacity gold
- Card elevation: subtle, not heavy

## 5. Component library

### Cards
- Use cards for thoughts, tasks, and lightweight content summaries.
- Keep card content minimal and scannable.
- Use a single clear primary action only when needed.

### Buttons
- Primary actions use gold fill.
- Secondary actions use outlined or low-emphasis styling.
- Buttons should feel confident and tactile, not overly playful.

### Sidebar
- Keep navigation calm and visually quiet.
- Use clear labels and low visual noise.
- Sidebar content should support focus rather than compete with the main canvas.

### Headers
- Headers should be minimal and spacious.
- Use clear title hierarchy and avoid unnecessary ornamentation.

### Floating CC logo
- The floating CC logo is a tactile, signature action surface.
- It should remain visible and easy to reach in landscape tablet use.
- It should feel premium, not decorative.

### Badges
- Badges are used for status or category cues.
- Keep them understated and consistent.
- Avoid excessive color variety.

### Lists
- Lists should feel calm and easy to scan.
- Use consistent spacing and group related items clearly.

## 6. Motion system

### Principles
- Motion should feel subtle, purposeful, and calm.
- Avoid playful bounce or excessive movement.
- Animation should support clarity and focus.

### Motion patterns
- Fade animations: use for logo reveal and lightweight onboarding moments.
- Navigation: use simple, understated transitions.
- Card transitions: use soft entrance or state changes.
- Success animations: use brief confirmation rather than spectacle.
- AI animations: use low-contrast, calm feedback states.

### Guidance
- Prefer durations around 800ms to 1200ms for major reveal moments.
- Avoid excessive motion on dense content surfaces.

## 7. S Pen interaction guidelines

### Writing experience
- The writing surface should feel like paper, not a generic text field.
- Input should feel immediate and low-friction.
- The system should support handwriting as the preferred capture method.

### Canvas behavior
- The canvas should be spacious and calm.
- Allow generous empty space around ink strokes.
- Keep tools visually quiet and unobtrusive.

### Recognition flow
- Recognition should be transparent and non-blocking.
- The system should support a future AI recognition layer without tying the design to a single provider.
- Confirmations should feel helpful and lightweight.

### Confirmation feedback
- After recognition or processing, provide subtle confirmation.
- Do not interrupt the user with noisy success states.

## 8. Accessibility

### Contrast
- Maintain strong contrast between text and background.
- Keep gold highlights from becoming the only source of emphasis.

### Touch targets
- Interactive targets should be comfortably large enough for tablet use.
- Maintain clear hit areas for buttons and floating actions.

### Font scaling
- Support system font scaling without breaking layout.
- Ensure text remains readable and components do not become cramped.

### Landscape tablet usability
- Preserve generous spacing in landscape orientation.
- Avoid overloading the main content area with too many competing elements.
- Keep key actions easy to reach without crowding the screen.

## 9. Implementation notes for future UI work

This design system is intended to guide future product screens and components in a way that is:
- calm and premium
- aligned to the Command Center brand
- optimized for tablet landscape
- compatible with future AI-assisted experiences

The design language should remain consistent across:
- StartScreen
- Gedachten Inbox
- Quick note capture
- future AI-assisted processing flows
- future Card, Project, and Calendar experiences
