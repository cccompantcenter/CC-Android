# CC-003 – Navigation & Information Flow

## 1. Purpose
This document defines how users move through Command Center and how information should flow between core areas.

Navigation must support four essential user needs:
- quick capture,
- processing,
- overview,
- deep work.

The experience should remain calm and direct: users must be able to capture ideas immediately, process them when ready, and navigate to structured execution contexts without losing the original source.

## 2. Core navigation principle
- Command Center is S Pen-first.
- The app must support fast capture without forcing the user through many screens.
- The user must always be able to return to the main overview.

Interpretation:
- Capture should be available within one intent from primary surfaces.
- Processing should be optional immediately after capture, never mandatory.
- Main overview should remain a stable anchor point for orientation.

## 3. Main entry points
Main areas of the app:
- Start screen: central launch surface for capture and orientation.
- Vandaag: day-focused execution view for current priorities.
- Gedachten Inbox: unprocessed and newly captured thoughts.
- Cards: universal card list and management surface.
- Projects: grouped goal-oriented context linked to Cards.
- Agenda: time-based context linked to Cards and calendar items.
- Contacts: people/context relations linked to Cards.
- Archive: preserved completed/inactive history.
- Settings: configuration, preferences, and integration controls.

## 4. Primary flow
Primary user flow:

Start screen -> New Gedachte -> Gedachten Inbox -> Verwerken -> Card -> Destination -> Project / Action / Calendar / Contact / Archive

Flow intent:
- Start with low-friction capture.
- Route through Inbox for triage and processing.
- Convert to Card for structured work.
- Assign destination context for execution and retrieval.

## 5. Quick capture flow
Quick capture must allow users to store a Gedachte with minimal cognitive load.

Requirements for quick capture:
- From Start screen: immediate entry into handwriting capture.
- From any screen: persistent quick capture action remains available.
- Minimal interaction: capture and save in as few steps as possible.
- Handwritten input first: S Pen input is primary.
- Save without classification: no forced destination, type, or metadata during capture.

Outcome:
- Captured item is stored as Gedachte in Inbox state, ready for later processing.

## 6. Processing flow
Processing transforms a captured Gedachte into structured, actionable information while preserving source integrity.

Processing sequence:
- Open Gedachte.
- Review original handwriting.
- Choose processing action.
- Create or link Card.
- Select Destination.
- Preserve original Gedachte.

Design constraints:
- Original source remains visible and traceable during processing.
- Processing actions must be explicit and reversible where possible.
- Destination assignment should be clear, lightweight, and non-destructive.

## 7. Card navigation
Cards should be reachable from all relevant contexts and maintain consistent cross-link behavior.

Card access paths:
- From Vandaag.
- From Projects.
- From Agenda.
- From Contacts.
- From Gedachten.
- From linked Cards.

Navigation behavior:
- Opening a Card should preserve context breadcrumbs (or equivalent back path).
- Linked Cards should allow lateral movement without losing orientation.
- Card detail should expose linked objects for fast context switching.

## 8. Information hierarchy
Command Center information hierarchy:
- Gedachte as source.
- Card as central working object.
- Destination as context.
- Project / Action / Calendar / Contact as structured views.
- Archive as preserved history.

Hierarchy intent:
- Source fidelity at the bottom layer.
- Operational consistency at the Card layer.
- Context-specific execution in destination views.
- Long-term continuity through Archive.

## 9. Navigation rules
Mandatory rules:
- No duplicate isolated flows.
- No separate task/note/project systems.
- Every route should respect the Universal Card Architecture.
- Quick capture must always stay available.
- Back navigation must feel predictable.
- Archive must be accessible but not distracting.

Additional operational guidance:
- Prefer shared route patterns over feature-specific custom navigation stacks.
- Keep transitions low-noise and purposeful.
- Prevent dead-end screens; every key screen should offer a clear onward or return path.

## 10. Future implementation guidance
Future screens and modules should be added only when they align with:
- the Gedachte-centered domain model,
- the Universal Card Architecture,
- the defined navigation principles in this document.

Decision criteria for new screens:
- Does the screen improve capture, processing, overview, or deep work?
- Does it reuse Card-based information flow instead of creating a new silo?
- Does it preserve source-of-truth linkage to Gedachte?
- Does it maintain predictable navigation and quick return to overview?

If these criteria are not met, functionality should be integrated into existing surfaces rather than introduced as a separate navigation branch.
