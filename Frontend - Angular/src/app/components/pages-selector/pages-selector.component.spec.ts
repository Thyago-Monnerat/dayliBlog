import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PagesSelectorComponent } from './pages-selector.component';

describe('PagesSelectorComponent', () => {
  let component: PagesSelectorComponent;
  let fixture: ComponentFixture<PagesSelectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PagesSelectorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PagesSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
