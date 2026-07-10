# CC-001 – Domain Model

## Purpose

This document defines the foundational domain model for Command Center.

It establishes the source-of-truth principle around **Gedachte** and provides implementation guidance for all future features.

Command Center models the lifecycle of work rather than the structure of tasks.

Everything begins as a **Gedachte**. A Gedachte remains the immutable source of truth, while Cards evolve through different work phases during their lifetime without losing their relationship to the originating Gedachte.

---

# 1. Core Philosophy

- Everything starts as a **Gedachte**.
- A **Gedachte** is the original source of information.
- Cards, Actions, Projects, Calendar Items and other domain objects are derived from or related to a Gedachte.
- The original handwritten Gedachte is never overwritten.
- Command Center captures first and structures afterwards.
- Structure is the result of capturing information, never the prerequisite.

---

# 2. Core Objects

## Gedachte

The original handwritten thought captured by the user.

A Gedachte is immutable and always remains the primary source of truth.

---

## Card

A Card represents work derived from one or more Gedachten.

Unlike a Gedachte, a Card evolves throughout its lifetime as work progresses.

A Card is the operational representation of work while preserving its relationship to the originating Gedachte.

---

## Destination

A routing target that determines where a Card belongs within the system.

Examples include:

- Project
- Action
- Calendar
- Contact
- Waiting
- Archive

---

## Project

A collection of Cards working towards a shared outcome.

---

## Action

A concrete executable next step.

---

## Calendar Item

A time-based commitment or appointment.

---

## Archive

Long-term storage for inactive Cards.

---

# 3. Relationships

- A Gedachte may create or relate to one or more Cards.
- A Card may relate to:
  - Project
  - Action
  - Calendar Item
  - Contact
  - Archive
- A Gedachte always remains the original source regardless of downstream processing.
- Multiple Cards may originate from the same Gedachte when different work streams emerge.

---

# 4. Domain Dimensions

A Card consists of several independent dimensions.

These dimensions must never be mixed.

Each dimension represents a different aspect of a Card.

A change in one dimension must not automatically imply a change in another.

For example:

- Context does not determine Work Phase.
- Work Phase does not determine Priority.
- Planning does not determine Context.
- User interface views are derived from these dimensions and are not part of the domain model itself.

---

## Origin

Where does the Card originate?

Examples:

- Gedachte
- Manual
- AI-assisted
- Import

Origin never changes.

---

## Context

What is this Card about?

Examples:

- Project
- Person
- Subject
- Client
- Meeting
- Idea

Context is relatively stable throughout the lifetime of a Card.

---

## Work Phase

What is currently happening?

Examples:

- Action Required
- Waiting for Response
- Delegated
- Scheduled
- In Progress
- Completed
- Archived

Work Phase changes continuously throughout the lifetime of a Card.

---

## Priority

Relative importance.

Examples:

- High
- Normal
- Low

Priority may change independently from every other dimension.

---

## Planning

When should attention be given?

Examples:

- Today
- This Week
- Specific Date
- Someday

Planning may change without affecting Context or Work Phase.

---

# 5. Card Lifecycle

A Gedachte is static.

A Card is dynamic.

A Card evolves through multiple work phases while maintaining its relationship with the originating Gedachte.

Example:

Gedachte

↓

Card

↓

Action Required

↓

Waiting for Response

↓

Action Required

↓

Delegated

↓

Waiting for Response

↓

Completed

↓

Archived

The lifecycle represents operational progress.

It never modifies the original Gedachte.

A Card may transition between work phases multiple times.

The lifecycle reflects the progression of work rather than a linear processing pipeline.

---

# 6. Status Model

Processing status applies while transforming a Gedachte into structured domain objects.

Possible statuses:

- New
- Processing
- Converted
- Archived
- Deleted

These statuses describe processing only.

They are independent from the operational Work Phase of a Card.

---

# 7. S Pen First Principle

- Handwriting is the primary input method.
- Keyboard input is secondary.
- Every editable object must support handwritten input.
- Capturing information must always be faster than organizing it.

---

# 8. AI Principle

- AI may interpret.
- AI may summarize.
- AI may suggest.
- AI never overwrites the original Gedachte.
- AI proposes structure; the user remains in control.
- AI integrations must remain provider-independent.
- AI operates on top of the domain model and never replaces it.

---

# 9. Future Implementation Guidance

All future functionality must follow this domain model.

Do not create isolated task, project or note systems.

Every module should integrate into the Gedachte-centered architecture.

Every future domain object should either originate from, relate to, or enrich an existing Gedachte or Card.

---

# 10. Design Principles

- Capture before structure.
- A Gedachte is immutable.
- A Card is dynamic.
- Context and Work Phase are independent.
- Status is independent from Work Phase.
- User interface views are projections of the domain model.
- A Card never changes identity when moving between work phases.
- Work evolves; the originating Gedachte does not.
- AI assists but never replaces user intent.
- Every future feature must integrate into this model.

---

# Non-goals

This document does not define:

- UI layouts
- Screen design
- Persistence technology
- AI provider implementation