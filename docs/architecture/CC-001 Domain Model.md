# CC-001 – Domain Model

## Purpose
This document defines the foundational domain model for Command Center.
It establishes the source-of-truth principle around **Gedachte** and provides implementation guidance for all future features.

## 1. Core philosophy
- Everything starts as a **Gedachte**.
- A **Gedachte** is the original source.
- Cards, actions, projects, and calendar items are derived from or related to a Gedachte.
- The original handwritten Gedachte is never overwritten.

## 2. Core objects
- **Gedachte**: Original handwritten thought capture and primary source object.
- **Card**: Processed or structured representation linked to one or more Gedachten.
- **Destination**: Classification target that routes a Card (for example: Project, Action, Calendar, Contact, Wachten op, Archive).
- **Project**: Outcome-oriented grouping of related work.
- **Action**: Concrete next step that can be executed.
- **Calendar item**: Time-bound commitment or event.
- **Archive**: Long-term storage for inactive or completed items.

## 3. Relationships
- A **Gedachte** can become or relate to a **Card**.
- A **Card** can be linked to:
  - **Project**
  - **Action**
  - **Calendar item**
  - **Contact**
  - **Wachten op**
  - **Archive**
- A **Gedachte** remains the source of truth, regardless of downstream links or conversions.

## 4. Status model
Domain objects that move through the processing flow use the following statuses:
- **New**: Captured, not yet processed.
- **Processing**: Being interpreted, sorted, or refined.
- **Converted**: Mapped into one or more structured objects (for example Card/Action/Project/Calendar item).
- **Archived**: Intentionally retained for reference, not active.
- **Deleted**: Removed from active domain use (while preserving rules for original-source integrity where applicable).

## 5. S Pen-first principle
- Every editable object must support handwritten input as primary input.
- Keyboard input is secondary.
- Handwriting support is a domain requirement, not an optional UI enhancement.

## 6. AI principle
- AI may interpret, summarize, and suggest.
- AI must not overwrite the original Gedachte.
- AI-provider independence must be preserved in architecture and integration boundaries.

## 7. Future implementation guidance
- All future features should follow this domain model.
- Do not build separate isolated task, project, or note systems outside this model.
- New modules must integrate with Gedachte-centered source-of-truth behavior.

## Non-goals
- This document does not define UI components or screen layouts.
- This document does not prescribe persistence technology.
- This document does not specify provider-specific AI implementation details.
