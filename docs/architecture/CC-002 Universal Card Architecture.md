# CC-002 – Universal Card Architecture

## 1. Purpose
The Card is the central object in Command Center because it is the primary operational unit for turning thoughts into structured work.

A Gedachte captures raw intent, while the Card provides a consistent, actionable representation that can be organized, linked, reviewed, and completed.

Every meaningful object in the system is represented by a Card or is related to one. This keeps the product model coherent and prevents fragmentation across isolated feature silos.

## 2. Relationship with Gedachte
- Every Card originates from a Gedachte or can be linked back to a Gedachte.
- The original Gedachte always remains the source of truth.
- A Card never replaces the original handwritten Gedachte.
- Transformations (for example interpretation, summarization, or classification) create derived structure without mutating the original source.

## 3. Universal Card concept
A single universal Card model represents different types of information by combining:
- shared core fields,
- a type classification,
- linked domain objects,
- optional extensions.

This approach avoids separate task, note, and project data models for equivalent behaviors.

Examples of representations using the same Card model:
- Task
- Project
- Meeting
- Contact
- Note
- Idea
- Waiting for
- Someday
- Archive reference

The Card type and linked entities determine behavior and context, not a separate top-level model per category.

## 4. Core properties
Every Card should include at least the following properties:

- Unique ID: stable, globally unique identifier.
- Title: concise primary label.
- Description: detailed context or notes.
- Source Gedachte reference: link to origin Gedachte ID(s).
- Card type: semantic classification (for example Task, Project, Note).
- Status: lifecycle state.
- Priority: urgency/importance indicator.
- Created date: timestamp of creation.
- Modified date: timestamp of latest update.
- Tags: flexible keyword labels.
- Attachments: linked files and media metadata.
- Handwritten content: ink payload and rendering metadata.
- Linked Cards: cross-card references.
- Linked Project: project relation when applicable.
- Linked Calendar item: time-bound scheduling relation.
- Linked Contact: person/entity relation.

Implementation note: optional fields should be nullable or extension-based rather than requiring subtype-specific model forks.

## 5. Card lifecycle
A Card follows a lifecycle from capture to long-term retention.

Example flow:
Gedachte -> Processing -> Card -> Active -> Completed -> Archived

Lifecycle interpretation:
- Gedachte: original handwritten capture.
- Processing: interpretation/classification stage.
- Card: structured card created from or linked to Gedachte.
- Active: card is in current execution context.
- Completed: outcome achieved or no further action required.
- Archived: retained for reference and traceability.

At every stage, source linkage to Gedachte remains preserved.

## 6. Extensibility
Future functionality should extend the Card through composition and optional modules, not through replacement of the core model.

Examples of extensible capabilities:
- AI summaries
- OCR
- Voice notes
- Documents
- Images
- Checklists
- Location
- Reminders

Extensibility strategy:
- keep core Card contract stable,
- add optional feature payloads behind capability flags or extension fields,
- preserve backward compatibility for storage and synchronization.

## 7. User interface principles
A Card in the UI should support calm, tablet-first interaction with S Pen as primary input.

Required interaction behavior:
- S Pen-first editing: handwriting is first-class and directly editable.
- Read mode: clean, scan-friendly presentation.
- Edit mode: explicit editable state with minimal friction.
- Expand/collapse: progressive disclosure for dense content.
- Attachments: visible and actionable media/document area.
- Handwritten notes: preserved ink view and editable ink layer.
- Metadata section: status, priority, dates, tags, provenance.
- Linked objects section: projects, calendar items, contacts, linked cards.

UI state transitions should be predictable, low-noise, and non-destructive to source content.

## 8. Design principles
Architectural principles for Card-based modeling:

- One universal Card model.
- No duplicate task/note/project models.
- Composition over duplication.
- Future-proof architecture.
- AI provider independence.

Additional constraints:
- preserve source-of-truth linkage to Gedachte,
- avoid category-specific silos that duplicate lifecycle logic,
- ensure interoperability across modules through shared Card contracts.

## 9. Future implementation guidance
All future modules should build on this Card architecture as the common integration surface.

Guidance:
- New feature domains should attach behavior to Cards via composition.
- Domain-specific views may differ in presentation, but should not introduce disconnected base models.
- Processing pipelines (manual or AI-assisted) should enrich Cards while preserving Gedachte integrity.
- Planning, execution, collaboration, and archive workflows should interoperate through Card links and metadata.

By enforcing this architecture, Command Center remains coherent, scalable, and resilient as capabilities expand.
